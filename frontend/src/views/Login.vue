<template>
<div class="container">
<v-flex xs12 sm8 offset-sm2 md6 offset-md3>
    <v-card class='login-card'>
        <v-card-title>
            <h2>
                <v-icon large color="blue darken-1" style="margin-right:0.25em">fas fa-sign-in-alt</v-icon>
                登录
            </h2>
        </v-card-title>
        <v-form ref="loginForm" v-model="valid" lazy-validation>
            <v-text-field v-model="username" autofocus
                :rules="nameRules" label="用户名" required />
            
            <v-text-field v-model="password"
                :type="passwordVis?'text':'password'"
                :append-icon="passwordVis?'visibility':'visibility_off'"
                @click:append="passwordVis=!passwordVis"
                :rules="passwordRules" label="密码" required />
            <div>
                <v-btn :disabled="!valid" color="success" @click="loginSubmit" >
                    登录
                </v-btn>
                <v-btn color="error" @click="loginReset" >
                    清空
                </v-btn>
                <v-btn color="accent" to="/register" >
                    注册
                </v-btn>
            </div>
        </v-form>
    </v-card>
</v-flex>
</div>
</template>

<style scoped>
.container {
    width: 100%;
}
.login-card {
    margin: 0 auto;
    margin-top: 20px;
}

form {
    padding: 15px;
}
</style>

<script>
    import qs from 'querystring'

    export default {
    data() {
        return {
            username: '',
            password: '',
            passwordVis: false,
            nameRules: [
                v => !!v || '用户名不能为空'
            ],
            passwordRules: [
                v => v.length >= 6 || '密码不得少于6位'
            ],
            valid: false
        }
    },
    methods: {
        loginSubmit(){
            let vm = this;
            vm.$axios.post("/auth/login", qs.stringify({
                username: vm.username,
                password: vm.password
            }), { withCredentials: true })
            .then(response => {
                if(response.data.statusCodeValue === 200) {
                    vm.$store.commit("setJWT", { JWT: response.data.body, axios: vm.$axios})
                    vm.$axios.get("/profile")
                    .then((response) => {
                        vm.$store.commit("setUser", response.data)
                    })
                    .catch((error) => {
                        vm.$store.commit("removeJWT", vm.$axios)
                        vm.$store.commit("removeUser")
                    })
                    vm.$store.commit("setSuccessPrompt", "登录成功！")
                    vm.$router.push({name: "index"})
                } else {
                    // login error
                    vm.$store.commit("setErrorPrompt", response.data.body);
                }
            })
            .catch(error => {
                vm.$store.commit("setErrorPrompt", `登录失败！消息：${error}`)
            })
        },
        loginReset() {
            this.$refs.loginForm.reset();
        }
    }
}
</script>

