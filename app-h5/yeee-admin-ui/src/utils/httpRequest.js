import Vue from 'vue'
import axios from 'axios'
import router from '@/router'
import qs from 'qs'
import merge from 'lodash/merge'
import { clearLoginInfo } from '@/utils'
import isPlainObject from 'lodash/isPlainObject'

const baseUrl = () => {
  return process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl
}

const http = axios.create({
  // baseURL: '/proxyApi/',
  baseURL: baseUrl(),
  timeout: 1000 * 30,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})
http.type = 'json'
http.form = () => {
  http.type = 'form'
  return http
}
http.json = () => {
  http.type = 'json'
  return http
}

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
  var token = Vue.cookie.get('yeee-manageweb-token')
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + Vue.cookie.get('yeee-manageweb-token') // 请求头带上token
  }
  if (http.type === 'form') {
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8'
  } else {
    config.headers['Content-Type'] = 'application/json; charset=utf-8'
  }
  // 默认参数
  var defaults = {
    '_t': new Date().getTime()
  }

  // merge(config.params, defaults)
  if (isPlainObject(config.params)) {
    config.params = {
      ...config.params,
      ...defaults
    }
  }
  if (isPlainObject(config.data)) {
    config.data = {
      ...config.data,
      ...defaults
    }
    // if (/^application\/x-www-form-urlencoded/.test(config.headers['content-type'])) {
    if (http.type === 'form') {
      config.data = qs.stringify(config.data)
    } else {
      config.data = JSON.stringify(config.data)
    }
  }

  return config
}, error => {
  return Promise.reject(error)
})

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
  // console.log(response)
  if (response && response.data.code === 401) { // 401, token失效
    clearLoginInfo()
    router.push({ name: 'login' })
    return Promise.reject(response.message)
  }
  return response
}, error => {
  return Promise.reject(error)
})

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
}

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefultParams = true) => {
  var defaults = {
    't': new Date().getTime()
  }
  return openDefultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
  var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export default http
