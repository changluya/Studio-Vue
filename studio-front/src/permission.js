import router from './router'
import store from './store'  //引入vuex

router.beforeEach((to, from, next) => {
  store.dispatch('getSiteBasicConfig').then(() => {
    // 异步操作完成后，执行 next()
    next();
  }).catch((error) => {
    // 处理错误情况
    console.error(error);
    next(false); // 阻止导航
  });
})