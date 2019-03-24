import Vue from 'vue'

// router
import VueRouter from 'vue-router'
import routes from './router'

import iView from 'iview'
import 'iview/dist/styles/iview.css'
import waterfall from 'vue-waterfall2'
import lazyLoad from 'vue-lazyload'
import App from './App.vue'

Vue.use(VueRouter);
const router = new VueRouter({
  mode: 'history',
  routes: routes
})

Vue.use(iView)
Vue.use(waterfall)
Vue.use(lazyLoad, {
  preLoad: 0.75,
  error: "static/book.svg",
  loading: "static/book.svg"
})
Vue.config.productionTip = false

new Vue({
  el: '#app',
  render: h => h(App),
  router: router
})
