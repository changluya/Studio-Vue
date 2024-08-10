import Vue from 'vue';
import App from './App.vue';
// 引入router/index.js
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
 
Vue.config.productionTip = false;
Vue.use(ElementUI)
 
// new Vue({
//   router,
//   render: h => h(App),
// }).$mount('#app');
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})