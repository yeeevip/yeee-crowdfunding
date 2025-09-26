import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useCommonStore = defineStore('common', () => {
  // 页面文档可视高度(随窗口改变大小)
  const documentClientHeight = ref(0)
  
  // 导航条, 布局风格, default(默认) / inverse(反向)
  const navbarLayoutType = ref('inverse')
  
  // 侧边栏, 布局皮肤, light(浅色) / dark(黑色)
  const sidebarLayoutSkin = ref('dark')
  
  // 侧边栏, 折叠状态
  const sidebarFold = ref(false)
  
  // 侧边栏, 菜单
  const menuList = ref<any[]>([])
  const menuActiveName = ref('')
  
  // 主入口标签页
  const mainTabs = ref<any[]>([])
  const mainTabsActiveName = ref('')
  
  // 内容, 是否需要刷新
  const contentIsNeedRefresh = ref(false)

  function updateDocumentClientHeight(height: number) {
    documentClientHeight.value = height
  }

  function updateNavbarLayoutType(type: string) {
    navbarLayoutType.value = type
  }

  function updateSidebarLayoutSkin(skin: string) {
    sidebarLayoutSkin.value = skin
  }

  function updateSidebarFold(fold: boolean) {
    sidebarFold.value = fold
  }

  function updateMenuList(list: any[]) {
    menuList.value = list
  }

  function updateMenuActiveName(name: string) {
    menuActiveName.value = name
  }

  function updateMainTabs(tabs: any[]) {
    mainTabs.value = tabs
  }

  function updateMainTabsActiveName(name: string) {
    mainTabsActiveName.value = name
  }

  function updateContentIsNeedRefresh(val: boolean) {
    contentIsNeedRefresh.value = val
  }

  return {
    documentClientHeight,
    navbarLayoutType,
    sidebarLayoutSkin,
    sidebarFold,
    menuList,
    menuActiveName,
    mainTabs,
    mainTabsActiveName,
    contentIsNeedRefresh,
    updateDocumentClientHeight,
    updateNavbarLayoutType,
    updateSidebarLayoutSkin,
    updateSidebarFold,
    updateMenuList,
    updateMenuActiveName,
    updateMainTabs,
    updateMainTabsActiveName,
    updateContentIsNeedRefresh
  }
})
