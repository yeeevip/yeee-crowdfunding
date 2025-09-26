<template>
  <main class="site-content" :class="{ 'site-content--tabs': $route.meta.isTab }">
    <!-- 主入口标签页 s -->
    <el-tabs
      v-if="$route.meta.isTab"
      v-model="mainTabsActiveName"
      :closable="true"
      @tab-click="selectedTabHandle"
      @tab-remove="removeTabHandle">
      <el-tab-pane
        v-for="item in mainTabs"
        :key="item.name"
        :label="item.title"
        :name="item.name">
        <el-card :body-style="siteContentViewHeight">
          <iframe v-if="item.type === 'iframe'" :src="item.iframeUrl" width="100%" height="100%" frameborder="0" scrolling="yes"></iframe>
          <template v-else>
            <router-view v-if="item.name === mainTabsActiveName" v-slot="{ Component }">
              <keep-alive :key="item.name">
                <component :is="Component" />
              </keep-alive>
            </router-view>
          </template>
        </el-card>
      </el-tab-pane>
    </el-tabs>
    <!-- 标签页工具下拉菜单 -->
    <el-dropdown v-if="$route.meta.isTab" class="site-tabs__tools" :show-timeout="0">
      <i class="el-icon-arrow-down el-icon--right"></i>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="tabsCloseCurrentHandle">关闭当前标签页</el-dropdown-item>
          <el-dropdown-item @click="tabsCloseOtherHandle">关闭其它标签页</el-dropdown-item>
          <el-dropdown-item @click="tabsCloseAllHandle">关闭全部标签页</el-dropdown-item>
          <el-dropdown-item @click="tabsRefreshCurrentHandle">刷新当前标签页</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <!-- 主入口标签页 e -->
    <el-card v-else :body-style="siteContentViewHeight">
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
    </el-card>
  </main>
</template>

<script>
  import { useCommonStore } from '@/stores/common'
  import { isURL } from '@/utils/validate'

  export default {
    setup() {
      const commonStore = useCommonStore()

      return {
        commonStore
      }
    },
    data () {
      return {
      }
    },
    computed: {
      documentClientHeight: {
        get () { return this.commonStore.documentClientHeight }
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
      },
      siteContentViewHeight () {
        var height = this.documentClientHeight - 50 - 30 - 2
        if (this.$route.meta.isTab) {
          height -= 40
          return isURL(this.$route.meta.iframeUrl) ? { height: height + 'px' } : { minHeight: height + 'px' }
        }
        return { minHeight: height + 'px' }
      }
    },
    methods: {
      // tabs, 选中tab
      selectedTabHandle (pane, ev) {
        console.log('========== 标签页点击事件调试 ==========')
        console.log('pane 完整对象:', pane)
        console.log('pane 的所有属性:', Object.keys(pane))
        console.log('pane.name:', pane.name)
        console.log('pane.paneName:', pane.paneName)
        console.log('pane.label:', pane.label)
        console.log('pane.$attrs:', pane.$attrs)
        console.log('pane.props:', pane.props)
        console.log('当前 mainTabsActiveName:', this.mainTabsActiveName)
        console.log('当前 mainTabs:', this.mainTabs)
        console.log('=========================================')
        
        // 尝试不同的方式获取标签页名称
        let tabName = pane.name || pane.paneName || pane.$attrs?.name || pane.props?.name
        console.log('尝试获取的tabName:', tabName)
        
        // 如果还是获取不到，直接使用当前激活的标签页名称
        if (!tabName) {
          tabName = this.mainTabsActiveName
          console.log('使用 mainTabsActiveName:', tabName)
        }
        
        const targetTab = this.mainTabs.filter(item => item.name === tabName)[0]
        if (targetTab) {
          console.log('找到目标tab:', targetTab)
          
          // 对于动态路由，检查路由是否存在
          const allRoutes = this.$router.getRoutes()
          const routeExists = allRoutes.some(route => route.name === targetTab.name)
          console.log('路由是否存在:', routeExists)
          
          if (routeExists) {
            this.$router.push({ name: targetTab.name }).catch(err => {
              console.error('路由跳转失败:', err)
              // 如果按名称跳转失败，尝试按路径跳转
              if (targetTab.path) {
                console.log('尝试按路径跳转:', targetTab.path)
                this.$router.push(targetTab.path)
              }
            })
          } else {
            console.warn('路由不存在，需要重新添加:', targetTab.name)
            // 路由不存在，可能是动态路由，尝试直接跳转路径
            if (targetTab.path) {
              console.log('按路径跳转:', targetTab.path)
              this.$router.push(targetTab.path)
            } else {
              console.error('无法跳转，缺少路由路径信息')
            }
          }
        } else {
          console.error('未找到目标tab，tabName:', tabName)
          console.error('可用的tabs:', this.mainTabs.map(t => t.name))
        }
      },
      // tabs, 删除tab
      removeTabHandle (tabName) {
        this.mainTabs = this.mainTabs.filter(item => item.name !== tabName)
        if (this.mainTabs.length >= 1) {
          // 当前选中tab被删除
          if (tabName === this.mainTabsActiveName) {
            this.$router.push({ name: this.mainTabs[this.mainTabs.length - 1].name }, () => {
              this.mainTabsActiveName = this.$route.name
            })
          }
        } else {
          this.menuActiveName = ''
          this.$router.push({ name: 'home' })
        }
      },
      // tabs, 关闭当前
      tabsCloseCurrentHandle () {
        this.removeTabHandle(this.mainTabsActiveName)
      },
      // tabs, 关闭其它
      tabsCloseOtherHandle () {
        this.mainTabs = this.mainTabs.filter(item => item.name === this.mainTabsActiveName)
      },
      // tabs, 关闭全部
      tabsCloseAllHandle () {
        this.mainTabs = []
        this.menuActiveName = ''
        this.$router.push({ name: 'home' })
      },
      // tabs, 刷新当前
      tabsRefreshCurrentHandle () {
        var tempTabName = this.mainTabsActiveName
        this.removeTabHandle(tempTabName)
        this.$nextTick(() => {
          this.$router.push({ name: tempTabName })
        })
      }
    }
  }
</script>

