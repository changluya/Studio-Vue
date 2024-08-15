import router from './router'
import store from './store'  //引入vuex

router.beforeEach((to, from, next) => {
  console.log('router.beforeEach')
  // 获取网站基础配置信息
  store.dispatch('getSiteBasicConfig')
  next()
})