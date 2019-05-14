export default {
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
        },
        JWT(state) {
            return localStorage.getItem("JWT")
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
            state.prompt = prompt
        },
        setJWT(state, JWT) {
            localStorage.setItem("JWT", JWT)
            axios.defaults.headers.common["Authorization"] = 
                "Bearer " + JWT
        }
    }
}