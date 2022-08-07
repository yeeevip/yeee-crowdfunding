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
        <main-content v-if="!$store.state.common.contentIsNeedRefresh"/>
      </div>
    </template>
  </div>
</template>

<script>
import MainNavbar from './main-navbar'
import MainSidebar from './main-sidebar'
import MainContent from './main-content'
export default {
  provide () {
    return {
      // 刷新
      refresh () {
        this.$store.commit('common/updateContentIsNeedRefresh', true)
        this.$nextTick(() => {
          this.$store.commit('common/updateContentIsNeedRefresh', false)
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
      get () { return this.$store.state.common.documentClientHeight },
      set (val) { this.$store.commit('common/updateDocumentClientHeight', val) }
    },
    sidebarFold: {
      get () { return this.$store.state.common.sidebarFold }
    },
    userId: {
      get () { return this.$store.state.user.id },
      set (val) { this.$store.commit('user/updateId', val) }
    },
    userName: {
      get () { return this.$store.state.user.name },
      set (val) { this.$store.commit('user/updateName', val) }
    },
    avatar: {
      get () { return this.$store.state.user.avatar },
      set (val) { this.$store.commit('user/updateAvatar', val) }
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
