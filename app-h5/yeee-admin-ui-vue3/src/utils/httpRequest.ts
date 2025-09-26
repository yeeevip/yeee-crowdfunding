import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import { useRouter } from 'vue-router'
import qs from 'qs'
import merge from 'lodash/merge'
import isPlainObject from 'lodash/isPlainObject'
import { clearLoginInfo } from '@/utils'
import { ElMessage } from 'element-plus'
import envConfig from '@/config/env'

const baseUrl = (): string => {
  return envConfig.apiBaseUrl
}

// 扩展 axios 实例类型
interface ExtendedAxiosInstance extends AxiosInstance {
  type: 'json' | 'form'
  form: () => ExtendedAxiosInstance
  json: () => ExtendedAxiosInstance
  adornUrl: (actionName: string) => string
  adornParams: (params?: Record<string, any>, openDefaultParams?: boolean) => Record<string, any>
  adornData: (data?: Record<string, any>, openDefaultData?: boolean, contentType?: 'json' | 'form') => string
}

const http = axios.create({
  baseURL: baseUrl(),
  timeout: envConfig.timeout,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
}) as ExtendedAxiosInstance

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
http.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const token = localStorage.getItem('yeee-manageweb-token')
  if (token && config.headers) {
    config.headers['Authorization'] = 'Bearer ' + token
  }
  
  if (config.headers) {
    if (http.type === 'form') {
      config.headers['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8'
    } else {
      config.headers['Content-Type'] = 'application/json; charset=utf-8'
    }
  }
  
  // 默认参数
  const defaults = {
    '_t': new Date().getTime()
  }

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
http.interceptors.response.use((response: AxiosResponse) => {
  if (response && response.data.code === 401) { // 401, token失效
    clearLoginInfo()
    const router = useRouter()
    router.push({ name: 'login' })
    ElMessage.error('登录已过期，请重新登录')
    return Promise.reject(response.data.message)
  }
  return response
}, error => {
  if (error.response?.status === 401) {
    clearLoginInfo()
    const router = useRouter()
    router.push({ name: 'login' })
    ElMessage.error('登录已过期，请重新登录')
  } else {
    ElMessage.error(error.message || '网络错误')
  }
  return Promise.reject(error)
})

/**
 * 请求地址处理
 * @param actionName action方法名称
 */
http.adornUrl = (actionName: string): string => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (import.meta.env.DEV && import.meta.env.VITE_OPEN_PROXY ? '/proxyApi/' : baseUrl()) + actionName
}

/**
 * get请求参数处理
 * @param params 参数对象
 * @param openDefaultParams 是否开启默认参数?
 */
http.adornParams = (params: Record<string, any> = {}, openDefaultParams = true): Record<string, any> => {
  const defaults = {
    't': new Date().getTime()
  }
  return openDefaultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param data 数据对象
 * @param openDefaultData 是否开启默认数据?
 * @param contentType 数据格式
 */
http.adornData = (data: Record<string, any> = {}, openDefaultData = true, contentType: 'json' | 'form' = 'json'): string => {
  const defaults = {
    't': new Date().getTime()
  }
  data = openDefaultData ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export { http as httpRequest }
export default http
