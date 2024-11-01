import { defineConfig } from 'vite'
import vue2 from '@vitejs/plugin-vue2'

// https://vitejs.dev/config/
export default defineConfig({
  base: './', // 打包的静态资源引用路径
  plugins: [vue2()], // 放插件用的
  resolve: {
    alias: {
      '@': '/src' // 配置@/提示符
    }
  },
  // vite 相关配置
  // 配置服务端口地址
  server: {
    port: 80,
    host: true,
    open: true
  },
})
