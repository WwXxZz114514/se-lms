import Vue from 'vue'
import Router from 'vue-router'
import HallSeat from '@/pages/hallseat/index'
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/hall-seat',
      name: 'HallSeat',
      component: HallSeat
    },
    {
      path: '/login',
      name: 'Login ',
      component: () => import('@/pages/login/index.vue')
    },
    {
      path: '/order',
      name: 'Order ',
      component: () => import('@/pages/order/index.vue')
    }
  ]
})
