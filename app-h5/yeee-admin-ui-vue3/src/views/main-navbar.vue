<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">综合管理平台</a>
        <a class="site-navbar__brand-mini" href="javascript:;">yeee</a>
      </h1>
    </div>
    <div class="site-navbar__body clearfix">
      <!-- 左侧功能按钮 -->
      <div class="site-navbar__left">
        <el-button type="text" class="navbar-btn" @click="sidebarFold = !sidebarFold" title="折叠/展开侧边栏">
          <icon-svg style="font-size: 18px" name="zhedie"></icon-svg>
        </el-button>
        <el-button type="text" class="navbar-btn" @click="refresh()" title="刷新页面">
          <icon-svg style="font-size: 18px" name="sync"></icon-svg>
        </el-button>
      </div>

      <!-- 右侧功能按钮 -->
      <div class="site-navbar__right">
        <el-button type="text" class="navbar-btn" @click="fullscreenHandle()" title="全屏">
          <icon-svg style="font-size: 18px" name="fullscreen"></icon-svg>
        </el-button>

        <!-- 用户信息区域 -->
        <div class="site-navbar__user">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="navbar-btn el-dropdown-link">
              <img v-if="!avatar" src="@/assets/img/avatar.png" :alt="userName" class="user-avatar">
              <img v-else :src="avatar" class="user-avatar">
              <span class="user-name">{{ userName }}</span>
              <el-icon class="dropdown-arrow">
                <ArrowDown />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="updatePasswordHandle()">修改密码</el-dropdown-item>
                <el-dropdown-item @click="logoutHandle()">退出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
  </nav>
</template>

<script>
import { useCommonStore } from '@/stores/common'
import { useUserStore } from '@/stores/user'
import UpdatePassword from './main-navbar-update-password.vue'
import screenfull from 'screenfull'
import { clearLoginInfo } from '@/utils'
import { ArrowDown } from '@element-plus/icons-vue'

export default {
  setup() {
    const commonStore = useCommonStore()
    const userStore = useUserStore()

    return {
      commonStore,
      userStore
    }
  },
  inject: ['refresh'],
  data () {
    return {
      updatePassowrdVisible: false
    }
  },
  components: {
    UpdatePassword,
    ArrowDown
  },
  computed: {
    navbarLayoutType: {
      get () { return this.commonStore.navbarLayoutType }
    },
    sidebarFold: {
      get () { return this.commonStore.sidebarFold },
      set (val) { this.commonStore.updateSidebarFold(val) }
    },
    mainTabs: {
      get () { return this.commonStore.mainTabs },
      set (val) { this.commonStore.updateMainTabs(val) }
    },
    userName: {
      get () { return this.userStore.name }
    },
    avatar: {
      get () { return this.userStore.avatar }
    }
  },
  methods: {
    // 全屏
    fullscreenHandle () {
      if (!screenfull.enabled) {
        return this.$message({
          message: this.$t('fullscreen.prompt'),
          type: 'warning',
          duration: 500
        })
      }
      screenfull.toggle()
    },
    // 修改密码
    updatePasswordHandle () {
      this.updatePassowrdVisible = true
      this.$nextTick(() => {
        this.$refs.updatePassowrd.init()
      })
    },
    // 退出
    logoutHandle () {
      this.$confirm(`确定进行[退出]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.get('/manage/sys-user/logout').then(({data: res}) => {
          if (res && res.code === 200) {
            clearLoginInfo()
            this.$router.push({ name: 'login' })
          }
        })
      }).catch(() => {})
    }
  }
}
</script>
