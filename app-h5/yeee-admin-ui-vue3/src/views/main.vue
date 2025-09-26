<template>
  <div
    class="site-wrapper"
    :class="{ 'site-sidebar--fold': sidebarFold }"
    v-loading.fullscreen.lock="loading"
    element-loading-text="拼命加载中">
    <template v-if="!loading">
      <main-navbar />
      <main-sidebar />
      <div class="site-content__wrapper" :style="{ 'min-height': documentClientHeight + 'px' }">
        <main-content v-if="!commonStore.contentIsNeedRefresh"/>
      </div>
      <!-- 底部版权条带 -->
      <div class="site-footer-bar">
        <div class="site-footer-bar__content">
          <span class="copyright-text">&copy; {{ currentYear }} 一页一科技. 保留所有权利.</span>
          <span class="divider">|</span>
          <span class="version-text">版本 v1.0.0 | 技术支持: <a href="https://yeee.vip" target="_blank">YEEE.VIP</a></span>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { useCommonStore } from '@/stores/common'
import { useUserStore } from '@/stores/user'
import MainNavbar from './main-navbar.vue'
import MainSidebar from './main-sidebar.vue'
import MainContent from './main-content.vue'

export default {
  setup() {
    const commonStore = useCommonStore()
    const userStore = useUserStore()
    
    return {
      commonStore,
      userStore
    }
  },
  provide () {
    return {
      // 刷新
      refresh () {
        this.commonStore.updateContentIsNeedRefresh(true)
        this.$nextTick(() => {
          this.commonStore.updateContentIsNeedRefresh(false)
        })
      }
    }
  },
  data () {
    return {
      loading: true
    }
  },
  components: {
    MainNavbar,
    MainSidebar,
    MainContent
  },
  computed: {
    documentClientHeight: {
      get () { return this.commonStore.documentClientHeight },
      set (val) { this.commonStore.updateDocumentClientHeight(val) }
    },
    sidebarFold: {
      get () { return this.commonStore.sidebarFold }
    },
    userId: {
      get () { return this.userStore.id },
      set (val) { this.userStore.updateId(val) }
    },
    userName: {
      get () { return this.userStore.name },
      set (val) { this.userStore.updateName(val) }
    },
    avatar: {
      get () { return this.userStore.avatar },
      set (val) { this.userStore.updateAvatar(val) }
    },
    currentYear () {
      return new Date().getFullYear()
    }
  },
  created () {
    this.getUserInfo()
    this.listPerm()
  },
  mounted () {
    this.resetDocumentClientHeight()
  },
  methods: {
    // 重置窗口可视高度
    resetDocumentClientHeight () {
      this.documentClientHeight = document.documentElement['clientHeight']
      window.onresize = () => {
        this.documentClientHeight = document.documentElement['clientHeight']
      }
    },
    // 获取当前管理员信息
    getUserInfo () {
      this.$http.get('/manage/sys-user/getUserInfo').then(({data: res}) => {
        if (res && res.code === 200) {
          this.loading = false
          this.userId = res.data.id
          this.userName = res.data.username
          this.avatar = res.data.avatar
        }
      })
    },
    // 获取资源列表
    listPerm () {
      // this.$http.get('/menu/listPerm').then(({data: res}) => {
      this.$http.get('/manage/sys-menu/getAuthz').then(({data: res}) => {
        if (res && res.code === 200) {
          sessionStorage.setItem('roles', JSON.stringify(res.data.roles || '[]'))
          sessionStorage.setItem('permissions', JSON.stringify(res.data.stringPermissions || '[]'))
        } else {
          sessionStorage.setItem('roles', '[]')
          sessionStorage.setItem('permissions', '[]')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
/* 底部版权条带样式 - 仿顶部导航栏 */
.site-footer-bar {
  position: fixed;
  bottom: 0;
  right: 0;
  left: 0;
  z-index: 1020;
  height: 40px;
  //background-color: #334157; /* 与顶部导航栏相同的颜色 */
  //box-shadow: 0 -2px 4px rgba(0, 0, 0, .08);
  display: flex;
  align-items: center;
  justify-content: center;

  &__content {
    display: flex;
    align-items: center;
    color: #abb1ba;
    font-size: 14px;

    .copyright-text {
      color: #abb1ba;
    }

    .divider {
      margin: 0 15px;
      color: #abb1ba;
    }

    .version-text {
      color: #abb1ba;
      font-size: 13px;
    }
  }
}

/* 为主内容区域添加底部边距，避免被固定底部条遮挡 */
.site-content__wrapper {
  padding-bottom: 50px;
}
</style>
