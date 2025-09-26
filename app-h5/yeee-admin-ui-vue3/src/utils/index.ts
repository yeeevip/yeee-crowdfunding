import router from '@/router'
import { useUserStore } from '@/stores'

/**
 * 获取浏览器高度
 */
export function getClientHeight(): number {
  let winHeight = 0
  if (window.innerHeight) {
    winHeight = window.innerHeight
  } else if ((document.body) && (document.body.clientHeight)) {
    winHeight = document.body.clientHeight
  }
  return winHeight
}

/**
 * 获取uuid
 */
export function getUUID(): string {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    const r = Math.random() * 16 | 0
    const v = c === 'x' ? r : (r & 0x3 | 0x8)
    return v.toString(16)
  })
}

/**
 * 获取baseUrl
 */
export function baseUrl(): string | undefined {
  if (import.meta.env.MODE !== 'production') {
    return import.meta.env.VITE_APP_BASE_API
  }
}

export function baseUrl2(): string {
  console.log(import.meta.env)
  const url = import.meta.env.VITE_APP_BASE_HOST_URL
  return (url === '/' ? '' : url + '/')
}

/**
 * 获取svg图标(id)列表
 */
export function getIconList(): string[] {
  const res: string[] = []
  document.querySelectorAll('svg symbol').forEach(item => {
    res.push(item.id)
  })
  return res
}

/**
 * 是否有权限
 * @param key 权限key
 */
export function hasRole(key: string): boolean {
  return JSON.parse(sessionStorage.getItem('roles') || '[]').indexOf(key) !== -1 || false
}

/**
 * 是否有权限
 * @param key 权限key
 */
export function hasPermission(key: string): boolean {
  return JSON.parse(sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false
}

/**
 * 树形数据转换
 * @param data 数据
 * @param id id字段名
 * @param pid 父id字段名
 */
export function treeDataTranslate(data: any[], id = 'id', pid = 'parentId'): any[] {
  const res: any[] = []
  const temp: Record<string, any> = {}
  
  for (let i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i]
  }
  
  for (let k = 0; k < data.length; k++) {
    if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
      if (!temp[data[k][pid]]['children']) {
        temp[data[k][pid]]['children'] = []
      }
      if (!temp[data[k][pid]]['_level']) {
        temp[data[k][pid]]['_level'] = 1
      }
      data[k]['_level'] = temp[data[k][pid]]._level + 1
      temp[data[k][pid]]['children'].push(data[k])
    } else {
      res.push(data[k])
    }
  }
  return res
}

/**
 * 清除登录信息
 */
export function clearLoginInfo(): void {
  localStorage.removeItem('yeee-manageweb-token')
  sessionStorage.removeItem('menuList')
  sessionStorage.removeItem('dynamicMenuRoutes')
  sessionStorage.removeItem('roles')
  sessionStorage.removeItem('permissions')
  
  // 清除 Pinia store 数据
  const userStore = useUserStore()
  userStore.updateId(0)
  userStore.updateName('')
  userStore.updateAvatar('')
  
  // 重置路由状态
  if (router.options) {
    (router as any).isAddDynamicMenuRoutes = false
  }
}
