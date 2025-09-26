import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router/index'

// Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// Element Plus 中文语言包
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 样式文件 - 必须在 Element Plus 之后导入以确保样式优先级
import './assets/scss/index.scss'

// Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './assets/icons/index.ts'

// 图标组件
import IconSvg from '@/components/icon-svg/index.vue'

// 工具函数
import httpRequest from './utils/httpRequest'
import { hasPermission, hasRole } from './utils'
import cloneDeep from 'lodash/cloneDeep'
import query from './utils/query.ts'

// 代码高亮
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.css'

// 抑制 ResizeObserver 循环警告
const resizeObserverErrorHandler = (e: Event) => {
  if (e.target === window && e.type === 'error' && (e as ErrorEvent).message === 'ResizeObserver loop completed with undelivered notifications.') {
    e.stopImmediatePropagation()
  }
}
window.addEventListener('error', resizeObserverErrorHandler)

// 创建Vue应用
const app = createApp(App)

// 创建Pinia store
const pinia = createPinia()

// 使用插件
app.use(pinia)
app.use(router)
app.use(ElementPlus, {
  locale: zhCn, // 设置中文语言包
})

// 注册所有 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 注册全局组件（在 Element Plus 图标之后，确保不被覆盖）
app.component('IconSvg', IconSvg)

// 全局属性
app.config.globalProperties.$http = httpRequest
app.config.globalProperties.$hasPerm = hasPermission
app.config.globalProperties.$hasRole = hasRole
app.config.globalProperties.$query = query

// 全局指令
app.directive('highlight', {
  mounted(el) {
    const blocks = el.querySelectorAll('pre code')
    blocks.forEach((block: HTMLElement) => {
      hljs.highlightElement(block)
    })
  }
})

// 全局配置
app.config.globalProperties.$ELEMENT = { size: 'small', zIndex: 3000 }

// 初始化全局配置对象
if (!window.SITE_CONFIG) {
  window.SITE_CONFIG = {
    dynamicRoutes: [],
    contentTabDefault: {}
  }
}

// 挂载应用
app.mount('#app')
