import VuexPersist from 'vuex-persist'
const store_persist = new VuexPersist({
    key: "data",
    storage: localStorage
})

export default {
    state: {
        user: {},
        prompt: "",
        JWT: null
    },
    getters: {
        user(state) {
            return state.user
        },
        prompt(state) {
            return state.prompt
        },
        JWT(state) {
            return state.JWT
        }
    },
    mutations: {
        setUser(state, user) {
            state.user = user
        },
        removeUser(state) {
            state.user = {}
        },
        setPrompt(state, prompt) {
            state.prompt = prompt
        },
        setJWT(state, {JWT, axios}) {
            state.JWT = JWT
            axios.defaults.headers.common["Authorization"] = 
                "Bearer " + JWT
        },
        removeJWT(state, axios) {
            state.JWT = null
            console.log("HERE")
            delete axios.defaults.headers.common.Authorization
        }
    },
    plugins: [store_persist.plugin]
}