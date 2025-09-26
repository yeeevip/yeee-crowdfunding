import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  base: './',
  plugins: [
    vue(),
    // vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  build: {
    // 优化包大小和代码分割
    rollupOptions: {
      output: {
        // 手动分包
        manualChunks(id) {
          // 将 node_modules 中的依赖按类型分组
          if (id.includes('node_modules')) {
            if (id.includes('vue') || id.includes('vue-router')) {
              return 'vue'
            }
            if (id.includes('element-plus')) {
              return 'element-plus'
            }
            if (id.includes('axios') || id.includes('lodash') || id.includes('qs')) {
              return 'vendor'
            }
            return 'libs'
          }
        },
        // 分别设置不同类型文件的输出目录
        chunkFileNames: 'js/[name]-[hash].js',
        entryFileNames: 'js/[name]-[hash].js',
        assetFileNames: (assetInfo) => {
          if (!assetInfo.name) {
            return 'assets/[name]-[hash].[ext]'
          }
          
          const info = assetInfo.name.split('.')
          let extType = info[info.length - 1]
          
          // 根据文件类型分配到不同文件夹
          if (/\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/i.test(assetInfo.name)) {
            extType = 'media'
          } else if (/\.(png|jpe?g|gif|svg|ico|webp)(\?.*)?$/i.test(assetInfo.name)) {
            extType = 'images'
          } else if (/\.(woff2?|eot|ttf|otf)(\?.*)?$/i.test(assetInfo.name)) {
            extType = 'fonts'
          } else if (/\.css$/i.test(assetInfo.name)) {
            extType = 'css'
          }
          
          return `${extType}/[name]-[hash].[ext]`
        }
      }
    },
    // 调整包大小警告限制 (Vue 的包天然比较大)
    chunkSizeWarningLimit: 2500
  },
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8801',
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
