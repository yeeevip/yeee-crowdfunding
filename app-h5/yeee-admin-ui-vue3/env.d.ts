/// <reference types="vite/client" />

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module 'highlight.js' {
  const hljs: any
  export default hljs
}

declare module 'lodash/cloneDeep' {
  const cloneDeep: any
  export default cloneDeep
}

interface Window {
  SITE_CONFIG: any
}
