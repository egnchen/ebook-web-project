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
            localStorage.setItem("JWT", JWT)
            axios.defaults.headers.common["Authorization"] = 
                "Bearer " + JWT
        },
        removeJWT(state, axios) {
            state.JWT = null
            localStorage.removeItem("JWT")
            delete axios.defaults.headers.common.Authorization
            console.log(axios.defaults.headers.common)
        }
    }
}