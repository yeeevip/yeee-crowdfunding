<template>
  <div class="mod-article">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-select v-model="dataForm.classifyId" placeholder="分类" clearable>
          <el-option
            v-for="item in classifyList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.title" placeholder="标题" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.oriFlag" clearable placeholder="是否原创" style="width: 120px;">
          <el-option label="是" value=1 />
          <el-option label="否" value=0 />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable placeholder="发布状态" style="width: 120px;">
          <el-option label="已发布" value=1 />
          <el-option label="未发布" value=0 />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('blog:article:add')" type="primary" size="default" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('blog:article:del')" type="danger" size="default" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
        <el-button type="warning" size="default" @click="showEditTip()" :icon="Edit">文章编写</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
              @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="title" label="标题" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-button type="primary" link @click="handleToEditTxtPage(scope.row)">{{ scope.row.title }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="titleImg" label="标题图" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-popover
            placement="right"
            width="400"
            trigger="click">
            <template #reference>
              <img :src="scope.row.titleImg" width="50px" height="50px" >
            </template>
            <img :src="scope.row.titleImg" width="400px" height="400px" >
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="作者" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="classifyName" label="分类" header-align="center" align="center"></el-table-column>
      <el-table-column prop="labelList" label="标签" header-align="center" align="center">
        <template #default="scope">
          <el-tag
            :key="label"
            v-for="label in scope.row.labelList"
            :disable-transitions="false">
            {{label}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="topicList" label="专题" header-align="center" align="center">
        <template #default="scope">
          <el-tag
            :key="topic"
            v-for="topic in scope.row.topicList"
            :disable-transitions="false">
            {{topic}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="oriFlag" label="是否原创" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.oriFlag === 1" type="success">是</el-tag>
          <el-tag v-else-if="scope.row.oriFlag === 0" type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="类型" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.type === 0" type="success">原文</el-tag>
          <el-tag v-else-if="scope.row.type === 1" type="danger">外链</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="readNum" label="阅读量" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="likeNum" label="点赞量" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="sort" label="排序" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="status" label="发布状态" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 1" type="success">已发布</el-tag>
          <el-tag v-else-if="scope.row.status === 0" type="danger">未发布</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="publishTime" label="发布时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template #default="scope">
          <el-button v-if="$hasPerm('blog:article:info')" type="primary" link size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>
          <el-button v-if="$hasPerm('blog:article:upd')" type="primary" link size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="修改"></el-button>
          <el-button v-if="$hasPerm('blog:article:del')" type="danger" link size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
        </template>
      </el-table-column>
  </el-table>
  <el-pagination v-if="this.gridOptions.isPage"
    @size-change="sizeChangeHandle"
    @current-change="currentChangeHandle"
    :current-page="pageNo"
    :page-sizes="pageSizes"
    :page-size="pageSize"
    :total="total"
    layout="->, total, sizes, prev, pager, next, jumper">
  </el-pagination>
  <!-- 弹窗, 新增 / 修改 -->
  <edit v-if="editVisible" ref="edit" @refreshDataList="listData"></edit>
  <!-- 弹窗, 详情 -->
  <info v-if="infoVisible" ref="info"></info>
  </div>
</template>

<script>
import edit from './article-edit.vue'
import info from './article-info.vue'
import grid from '@/mixins/grid'
import filePreview from '@/components/filePreview/index.vue'
import query from '@/utils/query'
import { mainRoutes, _import } from '@/router'
import { Document, Edit, Delete } from '@element-plus/icons-vue'
import { markRaw } from 'vue'
export default {
  mixins: [grid],
  data () {
    return {
      module: '/manage/blog/article/',
      gridOptions: {
        isQuery: true
      },
      defOrders: [
        {k: 'createTime', t: 'desc'}
      ],
      dataMode: {
        title: 'LK'
      },
      dataForm: {},
      classifyList: [],
      // 图标组件（使用 markRaw 避免响应式包装）
      Document: markRaw(Document),
      Edit: markRaw(Edit),
      Delete: markRaw(Delete)
    }
  },
  mounted () {
    this.getClassifyList()
  },
  components: {
    edit, info, filePreview
  },
  methods: {
    getClassifyList () {
      let qry = query.new()
      query.toP(qry, 1, 100)
      this.$http.get('/manage/blog/classify/page' + '?query=' + encodeURIComponent(query.toJsonStr(qry)))
        .then(({ data: res }) => {
          if (res.code === 200) {
            this.classifyList = res.data.result
          }
        })
    },
    handleToEditTxtPage (row) {
      // 组装路由名称, 并判断是否已添加, 如是: 则直接跳转
      // return this.$router.push({ name: 'blog-article/articleEditTxt__29' })
      var routeName = `${this.$route.name}EditTxt__${row.id}`
      var routes = window.SITE_CONFIG['dynamicRoutes'].filter(item => item.name === routeName)
      if (routes.length >= 1) {
        return this.$router.push({name: routeName, query: { id: row.id }})
      }
      // 否则: 添加并全局变量保存, 再跳转
      // 使用固定的组件导入路径，兼容 Vite 的动态导入限制
      let componentImporter
      try {
        // 使用 Vite 支持的动态导入模式
        const modules = import.meta.glob('@/views/**/*.vue')
        const moduleKey = '/src/views/modules/blog/article/articleEditTxt.vue'

        if (modules[moduleKey]) {
          componentImporter = modules[moduleKey]
          console.log('组件加载成功:', moduleKey)
        } else {
          console.warn('组件不存在，使用404页面:', moduleKey)
          componentImporter = () => import('@/views/common/404.vue')
        }
      } catch (error) {
        console.error('组件加载失败，使用404页面:', error)
        componentImporter = () => import('@/views/common/404.vue')
      }

      var route = {
        path: `/${routeName}`,
        component: componentImporter,
        name: routeName,
        meta: {
          ...window.SITE_CONFIG['contentTabDefault'],
          title: `编辑 - ${row.title}`,
          isTab: true,
          isDynamic: true
        }
      }
      // Vue Router 4.x 使用 addRoute 代替 addRoutes
      const dynamicMainRoute = {
        ...mainRoutes,
        name: `main-dynamic__${route.name}`,
        children: [route]
      }
      this.$router.addRoute(dynamicMainRoute)
      window.SITE_CONFIG['dynamicRoutes'].push(route)
      return this.$router.push({name: routeName, query: { id: row.id }})
    },
    showEditTip () {
      this.$alert('请【双击文章标题】进入编辑页面！！！', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      })
    }
  }
}
</script>
