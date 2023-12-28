import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import { post, get, patch, put, Delete } from './http'
Vue.config.productionTip = false
// 正式环境
Vue.prototype.interfaceURL = '172.29.17.63:8080'
// if (process.env.NODE_ENV === 'development') {
//   // 定义API接口调用专有地址（开发环境的场合）
//   Vue.prototype.interfaceURL = '/api/'
// }
// 定义全局变量
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$patch = patch
Vue.prototype.$put = put
Vue.prototype.$delete = Delete

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
