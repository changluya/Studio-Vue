import Vue from "vue";
import VueRouter from 'vue-router'
// 引入组件
import Index from '@/views/index.vue'
import Team from '@/views/team.vue'
import Time from '@/views/time.vue'

Vue.use(VueRouter)

//定义路由组件
const routers = [
  {
    path: '',
    redirect: 'index',  //重定向到/index，也就是主页
    component: Index,
    children: [         //子路由
        {
            path: 'index',
            component: () => import('@/views/index.vue'),
            name: 'Index'
        }
    ]
  },
  {
    path: '/team',
    component: Team,
    name: 'Team',
  },
  {
    path: '/time',
    component: Time,
    name: 'Time',
  }
]

//创建router实例
const router = new VueRouter({
    // mode: 'history',
    routes: routers
})

export default router;