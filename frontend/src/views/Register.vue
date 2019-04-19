<template>
<div class="container">
<v-flex xs12 sm8 offset-sm2>
    <v-card class='register-card'>
        <v-card-title>
            <h2>
                <v-icon large color="blue darken-1" style="margin-right:0.25em">fas fa-user-plus</v-icon>
                注册
            </h2>
        </v-card-title>
        <v-form ref="loginForm" v-model="valid" lazy-validation>
            <v-text-field v-model="username" autofocus
                :rules="nameRules" label="用户名" required />
            <v-text-field v-model="email"
                :rules="emailRules" label="邮箱" required />
            
            <v-text-field v-model="password"
                :type="passwordVis?'text':'password'"
                :append-icon="passwordVis?'visibility':'visibility_off'"
                @click:append="passwordVis=!passwordVis"
                :rules="passwordRules" label="密码" required />
            <v-text-field v-model="password_check"
                :type="passwordVis?'text':'password'"
                :append-icon="passwordVis?'visibility':'visibility_off'"
                @click:append="passwordVis=!passwordVis"
                :rules="passwordCheckRules" label="确认密码" required />
            <v-btn :disabled="!valid" color="success" @click="registerSubmit" >
                注册
            </v-btn>
        </v-form>
    </v-card>
</v-flex>
<v-snackbar v-model="snackBarVis" :top="true" :timeout="2000">
    {{ snackBarPrompt }}
</v-snackbar>
</div>
</template>

<style scoped>
.register-card {
    padding: 15px;
}
</style>

<script>
import axios from 'axios'

export default {
    data(){
        return {
            username: "eyek",
            email: "cyj205@sjtu.edu.cn",
            password: "123456",
            password_check: "123456",
            passwordVis: false,
            valid: false,
            snackBarPrompt: '',
            snackBarVis: false,

            nameRules: [
                v => !!v || '用户名不能为空！'
            ],
            emailRules: [
                v => !!v || '邮箱不能为空！',
                v => /[^@]+@[^\\.]+\.\w+/.test(v) || '格式不正确！'
            ],
            passwordRules: [
                v => !!v || '密码不能为空！',
                v => v.length >= 6 || '密码不能少于六位！'
            ],
            passwordCheckRules: [
                v => v === this.password || '不匹配'
            ]
        }
    },
    methods: {
        registerSubmit() {
            console.log("trying to register...")
            // build user
            var newUser = {
                username: this.username,
                password: this.password,
                email: this.email
            }
            var vm = this
            axios.post("http://localhost:8080/api/register", newUser)
            .then(function(response){
                vm.snackBarPrompt = `注册成功！用户名：${vm.username}`
                vm.snackBarVis = true
                console.log(response)
            })
            .catch(function(error){
                vm.snackBarPrompt = `失败！错误：${error}`
                vm.snackBarVis = true
            });
        }
    }

}
</script>
