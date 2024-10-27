import { defineConfig, loadEnv } from 'vite'
import path from 'path'
import createVitePlugins from './vite/plugins'

// https://vitejs.dev/config/
export default defineConfig(({ mode, command }) => {
  const env = loadEnv(mode, process.cwd())
  const { VITE_APP_ENV } = env
  return {
    base: './', // 打包的静态资源引用路径
    plugins: createVitePlugins(env, command.includes('build')), // 放插件用的
    resolve: {
      alias: {
        // 设置路径
        '~': path.resolve(__dirname, './'),
        // 设置别名
        '@': path.resolve(__dirname, './src')
      },
      extensions: ['.js', '.jsx', '.ts', '.tsx', '.json', '.vue'],
    },
    // vite 相关配置
    // 配置服务端口地址
    server: {
      port: 81,
      host: true,
      open: true
    },
  }
})