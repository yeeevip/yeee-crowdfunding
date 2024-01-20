import router from '@/router'
import store from '@/store'
// import API_CONFIG from '../../config/index'

/**
 * 获取浏览器高度
 */
export function getClientHeight () {
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
export function getUUID () {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
  })
}

/**
 * 获取baseUrl
 */
export function baseUrl () {
  // return process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl
}

export function baseUrl2 () {
  console.log(process.env)
  let url = process.env.VUE_APP_BASE_HOST_URL + process.env.VUE_APP_BASE_API
  return (url === '/' ? '' : url)
}

/**
 * 获取svg图标(id)列表
 */
export function getIconList () {
  var res = []
  document.querySelectorAll('svg symbol').forEach(item => {
    res.push(item.id)
  })
  return res
}

/**
 * 是否有权限
 * @param {*} key
 */
export function hasRole (key) {
  return JSON.parse(sessionStorage.getItem('roles') || '[]').indexOf(key) !== -1 || false
}

/**
 * 是否有权限
 * @param {*} key
 */
export function hasPermission (key) {
  return JSON.parse(sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false
}

/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export function treeDataTranslate (data, id = 'id', pid = 'parentId') {
  var res = []
  var temp = {}
  for (var i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i]
  }
  for (var k = 0; k < data.length; k++) {
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
export function clearLoginInfo () {
  localStorage.removeItem('yeee-manageweb-token')
  store.commit('resetStore')
  router.options.isAddDynamicMenuRoutes = false
}
