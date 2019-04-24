import Vue from 'vue'
import 'babel-polyfill'


// vue frameworks & plugins
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import waterfall from 'vue-waterfall2'
import App from './App.vue'
import axios from 'axios'

// Vuex
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
    state: {
        user: {
            username: "未登录",
            roles: {}
        },
        prompt: ""
    },
    getters: {
        user(state) {
            return state.user
        },
        prompt(state) {
            return state.prompt
        }
    },
    mutations: {
        setUser(state, user) {
            state.user = user
        },
        invalidateUser(state) {
            state.user = {
                username: "未登录",
                roles: {}
            }
        },
        setPrompt(state, prompt) {
            console.log(prompt)
            state.prompt = prompt
        }
    }
})

// router
import VueRouter from 'vue-router'
import routes from './router'
Vue.use(VueRouter)
const router = new VueRouter({
    mode: "history",
    routes
})

// axios config
axios.defaults.baseURL = 'http://localhost:8080/api/'
axios.defaults.withCredentials=true
Vue.prototype.$axios = axios;

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
    router: router,
    store
})
