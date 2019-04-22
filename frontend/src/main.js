import Vue from 'vue'
import 'babel-polyfill'

// router
import VueRouter from 'vue-router'
import routes from './router'

// vue frameworks & plugins
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import waterfall from 'vue-waterfall2'
import App from './App.vue'
import axios from 'axios'

// axios config
axios.defaults.baseURL = 'http://localhost:8080/api/';
Vue.prototype.$axios = axios;

Vue.use(VueRouter);
const router = new VueRouter({
  routes: routes,
  mode: "history"
})

Vue.use(Vuetify, {
  iconfont: 'fa',
  theme: {
    primary: '#3f51b5',
    secondary: '#03a9f4',
    accent: '#00bcd4',
    error: '#ff5722',
    warning: '#ffc107',
    info: '#607d8b',
    success: '#4caf50'
    }
})
Vue.use(waterfall)
Vue.config.productionTip = false

new Vue({
  el: '#app',
  render: h => h(App),
  router: router
})
