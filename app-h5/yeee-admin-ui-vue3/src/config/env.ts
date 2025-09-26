// 环境配置
interface EnvConfig {
  title: string
  version: string
  apiBaseUrl: string
  apiTarget: string
  timeout: number
  debug: boolean
}

// 开发环境配置
const developmentConfig: EnvConfig = {
  title: '一页一后台管理系统',
  version: 'v3.0.0',
  apiBaseUrl: '/api',  // 开发环境使用代理
  apiTarget: 'http://localhost:8801',
  timeout: 10000,
  debug: true
}

// 生产环境配置
const productionConfig: EnvConfig = {
  title: '一页一后台管理系统',
  version: 'v3.0.0',
  apiBaseUrl: 'http://localhost:8801',  // 生产环境直接访问后端API
  apiTarget: '',
  timeout: 10000,
  debug: false
}

// 测试环境配置
const testConfig: EnvConfig = {
  title: '一页一后台管理系统',
  version: 'v3.0.0',
  apiBaseUrl: 'http://test-api.com:8801',  // 测试环境直接访问测试API
  apiTarget: 'http://test-api.com:8801',
  timeout: 10000,
  debug: true
}

// 获取当前环境配置
const getEnvConfig = (): EnvConfig => {
  const env = import.meta.env.MODE || 'development'

  switch (env) {
    case 'production':
      return productionConfig
    case 'test':
      return testConfig
    default:
      return developmentConfig
  }
}

export const envConfig = getEnvConfig()
export default envConfig
