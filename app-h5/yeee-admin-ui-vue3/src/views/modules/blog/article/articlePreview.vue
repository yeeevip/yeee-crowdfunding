<template>
  <article>
    <!-- 顶部操作栏 -->
    <div class="toolbar-header">
      <div class="toolbar-left">
        <el-button type="info" icon="el-icon-arrow-left" @click="goBack()">返回</el-button>
      </div>
    </div>

    <!-- 文章标题显示 -->
    <div class="article-title-header" v-if="dataForm.title">
      <h2 class="article-title">{{ dataForm.title }}</h2>
      <div class="article-meta">
        <span class="preview-mode">预览模式</span>
        <span v-if="dataForm.createTime" class="create-time">创建时间：{{ dataForm.createTime }}</span>
        <span v-if="dataForm.viewCount" class="view-count">阅读：{{ dataForm.viewCount }}</span>
      </div>
    </div>

    <div class="infosbox">
      <div class="newsview">
        <div class="news_about">
          <strong>版权</strong>
          <span>
            本文为yeee.vip原创文章，转载无需和我联系，但请注明来自https://www.yeee.vip
          </span>
        </div>
        <div
          class="news_con ck-content"
          v-html="dataForm.content"
          v-highlight
        ></div>
      </div>
    </div>
  </article>
</template>

<script>
export default {
  data () {
    return {
      dataForm: {}
    }
  },
  activated () {
    const id = this.$route.params.id || this.$route.query.id
    console.log('activated - 路由参数:', this.$route.params, '查询参数:', this.$route.query, '最终ID:', id)
    this.init(id)
  },
  mounted () {
    const id = this.$route.params.id || this.$route.query.id
    console.log('mounted - 路由参数:', this.$route.params, '查询参数:', this.$route.query, '最终ID:', id)
    this.init(id)
  },
  methods: {
    init (id) {
      this.getInfo(id)
    },
    // 获取信息
    getInfo (id) {
      this.$http.json().post('/manage/blog/article/info', {
        'id': id
      }).then(({data: res}) => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.dataForm = {
          ...this.dataForm,
          ...res.data
        }
      }).catch(() => {})
    },
    // 返回上一个路由
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="scss">
  article {
    // 顶部工具栏
    .toolbar-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px 20px;
      border-bottom: 1px solid #e4e7ed;
      background: #fff;
      position: sticky;
      top: 0;
      z-index: 10;
      
      .toolbar-left {
        .el-button {
          margin-right: 10px;
        }
      }
    }
    
    // 文章标题显示区域
    .article-title-header {
      padding: 20px 20px 15px;
      border-bottom: 1px solid #e4e7ed;
      background: #fff;
      
      .article-title {
        margin: 0 0 10px 0;
        color: #333;
        font-size: 28px;
        font-weight: 600;
        line-height: 1.4;
      }
      
      .article-meta {
        display: flex;
        gap: 20px;
        align-items: center;
        color: #666;
        font-size: 14px;
        
        .preview-mode {
          background: #f6ffed;
          color: #52c41a;
          padding: 2px 8px;
          border-radius: 12px;
          font-size: 12px;
          font-weight: 500;
        }
        
        .create-time,
        .view-count {
          color: #999;
        }
      }
    }
  }

  .newsview {
    background-image: linear-gradient(90deg,rgba(50,0,0,.05) 3%,transparent 0),linear-gradient(1turn,rgba(50,0,0,.05) 3%,transparent 0);
    background-size: 20px 20px;
    background-position: 50%;
  }
</style>
