/**
 * 全站路由配置
 *
 * 建议:
 * 1. 代码中路由统一使用name属性跳转(不使用path属性)
 */
import { createRouter, createWebHashHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import http from '@/utils/httpRequest'
import { isURL } from '@/utils/validate'
import { clearLoginInfo } from '@/utils'
import { useCommonStore } from '@/stores/common'

// 动态导入组件，Vite 天然支持懒加载
const _import = (path: string) => {
  // 预定义的固定路由
  const fixedModules: Record<string, () => Promise<any>> = {
    'common/404': () => import('@/views/common/404.vue'),
    'common/login': () => import('@/views/common/login.vue'),
    'common/theme': () => import('@/views/common/theme.vue'),
    'common/iframe': () => import('@/views/common/iframe.vue'),
    'main': () => import('@/views/main.vue'),
    'modules/home': () => import('@/views/modules/home.vue')
  }

  // 如果是预定义的路由，直接返回
  if (fixedModules[path]) {
    return fixedModules[path]
  }

  // 动态导入其他组件，使用 try-catch 处理可能的导入错误
  try {
    const fullPath = `@/views/${path}.vue`
    // console.log(`[_import] 尝试动态导入组件: ${fullPath}`)

    // 使用 Vite 支持的动态导入模式
    const modules = import.meta.glob('@/views/**/*.vue')
    const moduleKey = `/src/views/${path}.vue`

    if (modules[moduleKey]) {
      // console.log(`[_import] 找到模块: ${moduleKey}`)
      return modules[moduleKey]
    } else {
      console.warn(`[_import] 组件不存在: ${fullPath}，使用404页面`)
      // console.log(`[_import] 可用的模块:`, Object.keys(modules).filter(key => key.includes('sys')))
      return () => import('@/views/common/404.vue')
    }
  } catch (error) {
    console.warn(`[_import] 无法加载组件: @/views/${path}.vue`, error)
    return () => import('@/views/common/404.vue')
  }
}

// 全局路由(无需嵌套上左右整体布局)
const globalRoutes: RouteRecordRaw[] = [
  { path: '/404', component: _import('common/404'), name: '404', meta: { title: '404未找到' } },
  { path: '/login', component: _import('common/login'), name: 'login', meta: { title: '登录' } }
]

// 主入口路由(需嵌套上左右整体布局)
export const mainRoutes: RouteRecordRaw = {
  path: '/',
  component: _import('main'),
  name: 'main',
  redirect: { name: 'home' },
  meta: { title: '主入口整体布局' },
  children: [
    // 通过meta对象设置路由展示方式
    // 1. isTab: 是否通过tab展示内容, true: 是, false: 否
    // 2. iframeUrl: 是否通过iframe嵌套展示内容, '以http[s]://开头': 是, '': 否
    // 提示: 如需要通过iframe嵌套展示内容, 但不通过tab打开, 请自行创建组件使用iframe处理!
    { path: '/home', component: _import('modules/home'), name: 'home', meta: { title: '首页' } },
    // 添加博客文章预览页面的静态路由支持
    { 
      path: '/blog-article/articlePreview__:id', 
      component: _import('modules/blog/article/articlePreview'), 
      name: 'blog-article-preview', 
      meta: { title: '文章预览', isTab: true }
    },
    { 
      path: '/blog-articleEditTxt__:id', 
      component: _import('modules/blog/article/articleEditTxt'), 
      name: 'blog-article-edit', 
      meta: { title: '文章编辑', isTab: true }
    }
    // { path: '/theme', component: _import('common/theme'), name: 'theme', meta: { title: '主题' } },
    // { path: '/demo-echarts', component: _import('demo/echarts'), name: 'demo-echarts', meta: { title: 'demo-echarts', isTab: true } },
    // { path: '/demo-ueditor', component: _import('demo/ueditor'), name: 'demo-ueditor', meta: { title: 'demo-ueditor', isTab: true } }
  ],
  beforeEnter (to, from, next) {
    let token = localStorage.getItem('yeee-manageweb-token')
    if (!token || !/\S/.test(token)) {
      clearLoginInfo()
      next({ name: 'login' })
    }
    next()
  }
}

const router = createRouter({
  history: createWebHashHistory(),
  scrollBehavior: () => ({ top: 0 }),
  routes: globalRoutes.concat(mainRoutes)
})

// 是否已经添加动态(菜单)路由
router.isAddDynamicMenuRoutes = false

router.beforeEach((to, from, next) => {
  // 添加动态(菜单)路由
  // 1. 已经添加 or 全局路由, 直接访问
  // 2. 获取菜单列表, 添加并保存本地存储
  if (router.isAddDynamicMenuRoutes || fnCurrentRouteType(to, globalRoutes) === 'global') {
    next()
  } else {
      http.get('/manage/sys-menu/listTree').then(({data: res}) => {
        if (res && res.code === 200) {
          fnAddDynamicMenuRoutes(res.data)
          router.isAddDynamicMenuRoutes = true
          sessionStorage.setItem('menuList', JSON.stringify(res.data || '[]'))

          // 同步更新到 store
          const commonStore = useCommonStore()
          commonStore.updateMenuList(res.data || [])

          // sessionStorage.setItem('permissions', JSON.stringify(res.permissions || '[]'))
          next({ ...to, replace: true })
        } else {
          sessionStorage.setItem('menuList', '[]')

        // 同步更新到 store
        const commonStore = useCommonStore()
        commonStore.updateMenuList([])

        // sessionStorage.setItem('permissions', '[]')
        next()
      }
    }).catch((e) => {
      console.log(`%c${e} 请求菜单列表和权限失败，跳转至登录页！！`, 'color:blue')
      router.push({ name: 'login' })
    })
  }
})

/**
 * 判断当前路由类型, global: 全局路由, main: 主入口路由
 * @param {*} route 当前路由
 */
function fnCurrentRouteType (route: any, globalRoutes: RouteRecordRaw[] = []): string {
  var temp: RouteRecordRaw[] = []
  for (var i = 0; i < globalRoutes.length; i++) {
    if (route.path === globalRoutes[i].path) {
      return 'global'
    } else if (globalRoutes[i].children && globalRoutes[i].children!.length >= 1) {
      temp = temp.concat(globalRoutes[i].children!)
    }
  }
  return temp.length >= 1 ? fnCurrentRouteType(route, temp) : 'main'
}

/**
 * 添加动态(菜单)路由
 * @param {*} menuList 菜单列表
 * @param {*} routes 递归创建的动态(菜单)路由
 */
function fnAddDynamicMenuRoutes (menuList: any[] = [], routes: RouteRecordRaw[] = []) {
  var temp: any[] = []
  for (var i = 0; i < menuList.length; i++) {
    if (menuList[i].children && menuList[i].children.length >= 1) {
      temp = temp.concat(menuList[i].children)
    } else if (menuList[i].url && /\S/.test(menuList[i].url)) {
      menuList[i].url = menuList[i].url.replace(/^\//, '')
      var route: RouteRecordRaw = {
        path: menuList[i].url.replace('/', '-'),
        component: () => import('@/views/common/404.vue'), // 默认组件，后面会被替换
        name: menuList[i].url.replace('/', '-'),
        meta: {
          menuId: menuList[i].id,
          title: menuList[i].name,
          isDynamic: true,
          isTab: true,
          iframeUrl: ''
        }
      }
      // url以http[s]://开头, 通过iframe展示
      if (isURL(menuList[i].url)) {
        route.path = `i-${menuList[i].id}`
        route.name = `i-${menuList[i].name}`
        route.meta!.iframeUrl = menuList[i].url
      } else {
        // 尝试加载动态组件，如果失败则使用404页面
        let componentPath = menuList[i].url
        // console.log(`[路径转换] 原始URL: "${componentPath}"`)

        if (componentPath.includes('/')) {
          // 处理类似 sys-user/user 的路径，转换为 modules/sys-user/user
          // console.log(`[路径转换] 包含斜杠，进行转换`)
          componentPath = `modules/${componentPath}`
        } else {
          // console.log(`[路径转换] 不包含斜杠，添加modules前缀`)
          componentPath = `modules/${componentPath}`
        }
        // console.log(`[路径转换] 最终路径: "${componentPath}"`)
        // console.log(`动态路由生成: url=${menuList[i].url}, componentPath=${componentPath}, name=${String(route.name)}`)
        route.component = _import(componentPath)
      }
      routes.push(route)
    }
  }
  if (temp.length >= 1) {
    fnAddDynamicMenuRoutes(temp, routes)
  } else {
    (mainRoutes as any).name = 'main-dynamic'
    mainRoutes.children = routes
    router.addRoute(mainRoutes)
    router.addRoute({ path: '/:pathMatch(.*)*', redirect: { name: '404' } })
    sessionStorage.setItem('dynamicMenuRoutes', JSON.stringify(mainRoutes.children || '[]'))
    // console.log('\n')
    // console.log('%c!<-------------------- 动态(菜单)路由 s -------------------->', 'color:blue')
    // console.log(mainRoutes.children)
    // console.log('%c!<-------------------- 动态(菜单)路由 e -------------------->', 'color:blue')
  }
}

// 扩展路由实例类型
declare module 'vue-router' {
  interface Router {
    isAddDynamicMenuRoutes: boolean
  }
}

export default router
export { _import }

