/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string
  readonly VITE_APP_VERSION: string
  readonly VITE_API_BASE_URL: string
  readonly VITE_API_TARGET: string
  readonly VITE_API_TIMEOUT: string
  readonly VITE_ROUTER_HISTORY: string
  readonly VITE_PUBLIC_PATH: string
  readonly VITE_OPEN_DEVTOOLS: string
  readonly VITE_MOCK_ENABLED: string
  readonly VITE_DEBUG: string
  readonly VITE_LOG_LEVEL: string
  readonly VITE_BUILD_GZIP: string
  readonly VITE_BUILD_ANALYZE: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
