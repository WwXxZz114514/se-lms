import Vue from 'vue'
import Router from 'vue-router'
import HallSeat from '@/pages/edit/HallSeat'
import SeatList from '@/pages/list/SeatList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/pages/login/index.vue')
    },
    {
      path: '/',
      name: 'seatList',
      component: SeatList
    },
    {
      path: '/hallSeat',
      name: 'hallSeat',
      component: HallSeat,
      beforeRouteLeave: (to, from, next) => {
        console.log('hello')
        next(false)
      }
    },
    {
      path: '/order',
      name: 'order',
      component: () => import('@/pages/order/index.vue')
    }
  ]
})
