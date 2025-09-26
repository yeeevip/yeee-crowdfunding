<template>
  <aside class="site-sidebar" :class="'site-sidebar--' + sidebarLayoutSkin">
    <div class="site-sidebar__inner">
      <el-menu
        :default-active="menuActiveName || 'home'"
        :collapse="sidebarFold"
        :unique-opened="true"
        :collapseTransition="false"
        class="site-sidebar__menu">
        <el-menu-item index="home" @click="$router.push({ name: 'home' })" >
          <el-icon class="aui-sidebar__menu-icon">
            <House />
          </el-icon>
          <span slot="title">首页</span>
        </el-menu-item>
        <sub-menu
          v-for="menu in menuList"
          :key="menu.id"
          :menu="menu"
          :dynamicMenuRoutes="dynamicMenuRoutes">
        </sub-menu>
      </el-menu>
    </div>
  </aside>
</template>

<script>
  import { useCommonStore } from '@/stores/common'
  import SubMenu from './main-sidebar-sub-menu.vue'
  import { isURL } from '@/utils/validate'
  import { House } from '@element-plus/icons-vue'

  export default {
    setup() {
      const commonStore = useCommonStore()

      return {
        commonStore
      }
    },
    data () {
      return {
        dynamicMenuRoutes: []
      }
    },
    components: {
      SubMenu,
      House
    },
    computed: {
      sidebarLayoutSkin: {
        get () { return this.commonStore.sidebarLayoutSkin }
      },
      sidebarFold: {
        get () { return this.commonStore.sidebarFold }
      },
      menuList: {
        get () { return this.commonStore.menuList },
        set (val) { this.commonStore.updateMenuList(val) }
      },
      menuActiveName: {
        get () { return this.commonStore.menuActiveName },
        set (val) { this.commonStore.updateMenuActiveName(val) }
      },
      mainTabs: {
        get () { return this.commonStore.mainTabs },
        set (val) { this.commonStore.updateMainTabs(val) }
      },
      mainTabsActiveName: {
        get () { return this.commonStore.mainTabsActiveName },
        set (val) { this.commonStore.updateMainTabsActiveName(val) }
      }
    },
    watch: {
      $route: 'routeHandle'
    },
    created () {
      const menuListData = JSON.parse(sessionStorage.getItem('menuList') || '[]')
      const dynamicMenuRoutesData = JSON.parse(sessionStorage.getItem('dynamicMenuRoutes') || '[]')

      // 同步数据到 store
      this.commonStore.updateMenuList(menuListData)
      this.dynamicMenuRoutes = dynamicMenuRoutesData

      // 监听动态路由添加事件
      window.addEventListener('dynamicRouteAdded', this.onDynamicRouteAdded)

      this.routeHandle(this.$route)
    },
    beforeUnmount () {
      // 清理事件监听器
      window.removeEventListener('dynamicRouteAdded', this.onDynamicRouteAdded)
    },
    methods: {
      // 处理动态路由添加事件
      onDynamicRouteAdded (event) {
        const { route } = event.detail
        // 更新本地动态路由列表
        const existingRoute = this.dynamicMenuRoutes.find(item => item.name === route.name)
        if (!existingRoute) {
          this.dynamicMenuRoutes.push(route)
        }
      },
      // 路由操作
      routeHandle (route) {
        if (route.meta.isTab) {
          // tab选中, 不存在先添加
          var tab = this.mainTabs.filter(item => item.name === route.name)[0]
          if (!tab) {
            if (route.meta.isDynamic) {
              route = this.dynamicMenuRoutes.filter(item => item.name === route.name)[0]
              if (!route) {
                return console.error('未能找到可用标签页!')
              }
            }
            tab = {
              menuId: route.meta.menuId || route.name,
              name: route.name,
              path: route.path,
              title: route.meta.title,
              type: isURL(route.meta.iframeUrl) ? 'iframe' : 'module',
              iframeUrl: route.meta.iframeUrl || ''
            }
            this.mainTabs = this.mainTabs.concat(tab)
          }
          this.menuActiveName = tab.menuId + ''
          this.mainTabsActiveName = tab.name
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
.aui-sidebar__menu-icon {
  font-size: 20px;
  margin-right: 0;
  color: inherit; // 确保图标继承父元素颜色
}
</style>
