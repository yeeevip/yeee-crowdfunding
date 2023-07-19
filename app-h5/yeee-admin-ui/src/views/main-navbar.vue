<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">综合管理平台</a>
        <a class="site-navbar__brand-mini" href="javascript:;">yeee</a>
      </h1>
    </div>
    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="zhedie"></icon-svg>
        </el-menu-item>
        <el-menu-item class="site-navbar__switch" index="1" @click="refresh()">
          <icon-svg name="sync"></icon-svg>
        </el-menu-item>
      </el-menu>
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="fullscreenHandle()">
          <icon-svg name="fullscreen"></icon-svg>
        </el-menu-item>
        <!-- <el-menu-item index="1" @click="$router.push({ name: 'theme' })">
          <template slot="title">
            <el-badge value="new">
              <icon-svg name="shezhi" class="el-icon-setting"></icon-svg>
            </el-badge>
          </template>
        </el-menu-item> -->
        <!-- <el-menu-item index="2">
          <el-badge value="hot">
            <a href="//www.baidu.com/" target="_blank">百度</a>
          </el-badge>
        </el-menu-item> -->
        <!-- <el-submenu index="3">
          <template slot="title">语言</template>
          <el-menu-item index="2-1"><a href="///www.baidu.com" target="_blank">中文</a></el-menu-item>
          <el-menu-item index="2-2"><a href="///www.baidu.com" target="_blank">英文</a></el-menu-item>
          <el-menu-item index="2-3"><a href="///www.baidu.com" target="_blank">繁体</a></el-menu-item>
        </el-submenu> -->
        <el-menu-item class="site-navbar__avatar" index="3">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <img v-if="!avatar" src="~@/assets/img/avatar.png" :alt="userName">
              <img v-else :src="avatar">
              {{ userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="$hasPerm('sys:user:updPwd')" @click.native="updatePasswordHandle()">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
  </nav>
</template>

<script>
import UpdatePassword from './main-navbar-update-password'
import screenfull from 'screenfull'
import { clearLoginInfo } from '@/utils'
export default {
  inject: ['refresh'],
  data () {
    return {
      updatePassowrdVisible: false
    }
  },
  components: {
    UpdatePassword
  },
  computed: {
    navbarLayoutType: {
      get () { return this.$store.state.common.navbarLayoutType }
    },
    sidebarFold: {
      get () { return this.$store.state.common.sidebarFold },
      set (val) { this.$store.commit('common/updateSidebarFold', val) }
    },
    mainTabs: {
      get () { return this.$store.state.common.mainTabs },
      set (val) { this.$store.commit('common/updateMainTabs', val) }
    },
    userName: {
      get () { return this.$store.state.user.name }
    },
    avatar: {
      get () { return this.$store.state.user.avatar }
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
