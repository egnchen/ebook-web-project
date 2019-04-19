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
<v-snackbar v-model="snackBarVis" :top="true" :timeout="2000">
    {{ snackBarPrompt }}
</v-snackbar>
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
import Axios from 'axios';
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
            snackBarVis: false,
            snackBarPrompt: ''
        }
    },
    methods: {
        loginSubmit(){
            axios.post("http://localhost:8080/perform-login", {
                params: {
                    username
                }
            })
            setTimeout(() => {
                this.snackBarPrompt = `登录成功！用户名：${this.username}`
                this.snackBarVis = true
            }, 500)
        },
        loginReset() {
            this.$refs.loginForm.reset();
        }
    }
}
</script>

