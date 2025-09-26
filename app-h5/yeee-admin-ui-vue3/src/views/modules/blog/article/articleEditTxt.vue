<template>
  <div class="mod-articleEditTxt">
    <!-- 顶部操作栏 -->
    <div class="toolbar-header">
      <div class="toolbar-left">
        <el-button type="info" icon="el-icon-arrow-left" @click="goBack()">返回</el-button>
      </div>
      <div class="toolbar-right">
        <el-button type="primary" @click="dataFormSubmit()">保存</el-button>
        <el-button @click="blogPreview()">预览</el-button>
      </div>
    </div>
    
    <!-- 文章标题显示 -->
    <div class="article-title-header" v-if="dataForm.title">
      <h2 class="article-title">{{ dataForm.title }}</h2>
      <div class="article-meta">
        <span class="edit-mode">编辑模式</span>
        <span v-if="dataForm.createTime" class="create-time">创建时间：{{ dataForm.createTime }}</span>
      </div>
    </div>
    
    <!-- 主内容区域 -->
    <div class="content-area">
      <el-form :model="dataForm" ref="dataForm" label-width="0px">
        <el-form-item label="" prop="content">
          <wangeditor ref="wangeditor" :getEditConts="getEditConts" :insertImg="insertImgHandle"></wangeditor>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import debounce from 'lodash/debounce'
import wangeditor from '@/components/wangeditor/index.vue'
import { mainRoutes } from '@/router'

export default {
  data () {
    return {
      id: null,
      visible: false,
      otherParamsVisible: false,
      roleList: [],
      orgList: [],
      dataForm: {
        id: null,
        content: '',
        title: ''
      },
      classifyList: [],
      labelList: [],
      topicList: []
    }
  },
  components: {
    wangeditor
  },
  mounted () {
    console.log('页面挂载，路由信息:', this.$route)
    console.log('URL参数 params:', this.$route.params)
    console.log('URL参数 query:', this.$route.query)
    // 从query参数获取ID，因为动态路由使用的是query传参
    const id = this.$route.query.id || this.$route.params.id
    console.log('获取到的ID:', id)
    this.init(id)
  },
  activated () {
    // keep-alive 组件激活时也执行初始化
    const id = this.$route.query.id || this.$route.params.id
    console.log('页面激活，获取到的ID:', id)
    this.init(id)
  },
  watch: {
    // 监听路由变化
    '$route' (to, from) {
      console.log('路由变化:', { from: from.fullPath, to: to.fullPath })
      console.log('新路由参数:', { params: to.params, query: to.query })
      const id = to.query.id || to.params.id
      if (id && id !== this.id) {
        console.log('检测到ID变化，重新初始化:', id)
        this.init(id)
      }
    }
  },
  methods: {
    init (id) {
      console.log('初始化页面，ID:', id)
      console.log('ID类型:', typeof id)
      console.log('ID是否有效:', !!id)
      
      this.dataForm.id = null
      this.dataForm.content = ''
      this.id = id
      this.visible = true
      
      this.$nextTick(() => {
        // 确保表单引用存在
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].resetFields()
        }
        
        if (this.id && this.id !== 'undefined' && this.id !== 'null') {
          console.log('准备获取文章信息，ID:', this.id)
          this.getInfo()
        } else {
          console.log('新建模式或ID无效，清空编辑器')
          // 新建时清空编辑器内容
          this.$nextTick(() => {
            if (this.$refs.wangeditor && this.$refs.wangeditor.setEditorContent) {
              this.$refs.wangeditor.setEditorContent('')
            }
          })
        }
      })
    },
    // 获取信息
    getInfo () {
      console.log('开始获取文章信息，发送请求，ID:', this.id)
      this.$http.json().post('/manage/blog/article/info', {
        'id': this.id
      }).then(({data: res}) => {
        console.log('获取文章信息响应:', res)
        if (res.code !== 200) {
          console.error('获取文章信息失败，返回码:', res.code, '错误信息:', res.msg)
          return this.$message.error(res.msg || '获取数据失败')
        }
        
        console.log('文章数据:', res.data)
        console.log('文章内容长度:', res.data.content ? res.data.content.length : 0)
        
        // 更新表单数据
        this.dataForm = {
          ...this.dataForm,
          ...res.data
        }
        
        console.log('更新后的表单数据:', this.dataForm)
        
        // 确保编辑器已准备好再设置内容
        this.$nextTick(() => {
          console.log('准备设置编辑器内容')
          console.log('编辑器引用存在:', !!this.$refs.wangeditor)
          console.log('setEditorContent方法存在:', !!(this.$refs.wangeditor && this.$refs.wangeditor.setEditorContent))
          
          if (this.$refs.wangeditor && this.$refs.wangeditor.setEditorContent) {
            const content = res.data.content || ''
            console.log('设置编辑器内容，内容长度:', content.length)
            this.$refs.wangeditor.setEditorContent(content)
            console.log('编辑器内容设置完成')
          } else {
            console.error('编辑器引用或方法不存在')
          }
        })
      }).catch((error) => {
        console.error('获取文章信息网络请求失败:', error)
        this.$message.error('获取文章信息失败: ' + error.message)
      })
    },
    // 表单提交
    dataFormSubmit: debounce(function () {
      // console.log(this.dataForm)
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          let submitData = {}
          submitData.id = this.dataForm.id
          submitData.content = this.dataForm.content
          this.$http.json().post('/manage/blog/article/upd-txt', submitData).then(({data: res}) => {
            if (res && res.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 500,
                onClose: () => {

                }
              })
            } else {
              this.$message.error(res.message)
            }
          })
        }
      })
    }, 1000, { 'leading': true, 'trailing': false }),
    blogPreview () {
      let row = this.dataForm
      // 组装路由名称, 并判断是否已添加, 如是: 则直接跳转
      var routeName = `blog-article/articlePreview__${row.id}`
      var route = window.SITE_CONFIG['dynamicRoutes'].filter(item => item.name === routeName)[0]
      if (route) {
        return this.$router.push({ name: routeName, query: { id: row.id } })
      }
      // 否则: 添加并全局变量保存, 再跳转
      route = {
        path: `/${routeName}`,
        // 使用动态 import 替代 require
        component: () => import(`@/views/modules/blog/article/articlePreview.vue`).catch(() => {
          console.warn(`组件不存在: @/views/modules/blog/article/articlePreview.vue`)
          return import('@/views/common/404.vue')
        }),
        name: routeName,
        meta: {
          ...window.SITE_CONFIG['contentTabDefault'],
          menuId: this.$route.meta.menuId,
          title: `预览 - ${row.title}`,
          isTab: true,
          isDynamic: true
        }
      }
      
      // 使用与其他动态路由相同的添加方式
      try {
        // 检查是否已有 main-dynamic 路由
        const allRoutes = this.$router.getRoutes()
        const mainDynamicRoute = allRoutes.find(r => r.name === 'main-dynamic')
        
        if (mainDynamicRoute) {
          // 如果已存在，直接添加子路由
          this.$router.addRoute('main-dynamic', route)
        } else {
          // 如果不存在，创建新的主路由
          const dynamicMainRoute = {
            ...mainRoutes,
            name: 'main-dynamic',
            children: [route]
          }
          this.$router.addRoute(dynamicMainRoute)
        }
        
        window.SITE_CONFIG['dynamicRoutes'].push(route)
        return this.$router.push({ name: routeName, query: { id: row.id } })
      } catch (error) {
        console.error('添加预览路由失败:', error)
        this.$message.error('打开预览页面失败')
      }
    },
    getEditConts (value) {
      this.dataForm.content = value
    },
    insertImgHandle (res, insertImg, editor) {

    },
    // 返回上一个路由
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>
<style lang="scss">
.mod-articleEditTxt {
  display: flex;
  flex-direction: column;
  height: 100%;
  
  // 顶部工具栏
  .toolbar-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 0;
    border-bottom: 1px solid #e4e7ed;
    background: #fff;
    flex-shrink: 0;
    
    .toolbar-left {
      .el-button {
        margin-right: 10px;
      }
    }
    
    .toolbar-right {
      .el-button {
        margin-left: 10px;
      }
    }
  }
  
  // 文章标题显示区域
  .article-title-header {
    padding: 20px 0 15px;
    border-bottom: 1px solid #e4e7ed;
    background: #fff;
    flex-shrink: 0;
    
    .article-title {
      margin: 0 0 10px 0;
      color: #333;
      font-size: 24px;
      font-weight: 600;
      line-height: 1.4;
    }
    
    .article-meta {
      display: flex;
      gap: 20px;
      align-items: center;
      color: #666;
      font-size: 14px;
      
      .edit-mode {
        background: #e6f7ff;
        color: #1890ff;
        padding: 2px 8px;
        border-radius: 12px;
        font-size: 12px;
        font-weight: 500;
      }
      
      .create-time {
        color: #999;
      }
    }
  }
  
  // 主内容区域
  .content-area {
    flex: 1;
    overflow-y: auto;
    padding-top: 15px;
    
    .el-form {
      height: 100%;
      
      .el-form-item {
        margin-bottom: 0;
        height: 100%;
        
        .el-form-item__content {
          height: 100%;
        }
      }
    }
  }
}

.mod-article-edit {
  .input-bar i {
    padding-top: 6px;
    font-size: 24px;
    color: #3a8ee6;
    transition-property: color;
    transition-duration: 0.15s;
    transition-timing-function: linear;
    transition-delay: initial;
  }
}
</style>
