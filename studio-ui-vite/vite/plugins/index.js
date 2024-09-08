import vue2 from '@vitejs/plugin-vue2'
import createSvgIcon from './svg-icon'

export default function createVitePlugins(viteEnv, isBuild = false) {
    const vitePlugins = [vue2()]
    vitePlugins.push(createSvgIcon(isBuild))
    return vitePlugins
}