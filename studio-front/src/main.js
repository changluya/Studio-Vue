import Vue from 'vue';
import App from './App.vue';
import store from './store'
// 引入router/index.js
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueScrollTo from 'vue-scrollto'
import scroll from 'vue-seamless-scroll'
// 图片预览组件
import ImagePreview from "@/components/ImagePreview/index.vue"
// 引入公共方法
import { getSiteConfig } from '@/utils/request'
// 引入常量
import { MY_CONSTANT } from '@/utils/constants';
// 权限控制
import './permission' 
 
Vue.config.productionTip = false;
Vue.use(ElementUI)


// 全局组件挂载
Vue.component('ImagePreview', ImagePreview)

// 全局方法挂载
Vue.prototype.$getSiteConfig = getSiteConfig
// 全局常量挂载
Vue.prototype.$MY_CONSTANT = MY_CONSTANT;
Vue.use(VueScrollTo)
Vue.use(scroll)
 
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})