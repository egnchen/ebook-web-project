<template>
<Card class="login-card">
    <p slot="title">
        <Icon type="ios-contact" />
        登录
    </p>
    <img src="static/logo.gif" class="logo" />
    <Form ref="loginForm" :model="loginForm" :rules="loginRules">
        <FormItem prop="user">
            <Input type="text" prefix="ios-contact" v-model="loginForm.user" placeholder="用户名" />
        </FormItem>
        <FormItem prop="password">
            <Input type="password" prefix="ios-lock-outline" v-model="loginForm.password" placeholder="密码" />
        </FormItem>
        <FormItem>
            <Button type="primary" @click="loginSubmit('loginForm')" class="submit-button">登录</Button>
        </FormItem>
    </Form>
</Card>
</template>

<style scoped>
.login-card {
    min-width: 400px;
    width: 40vw;
    margin: 0 auto;
    margin-top: 20px;
}

.logo {
    width: auto;
    height: auto;
    max-width: 100px;
    margin: 10px auto;
    display: block;
}
.submit-button {
    display: block;
    float: right;
}
</style>

<script>
export default {
    data() {
        return {
            loginForm: {
                user: '',
                password: ''
            },
            loginRules: {
                user: [
                    { required: true, message: "请输入用户名！", trigger: 'blur' }
                ],
                password: [
                    { required: true, message: "请输入密码！", trigger: 'blur' },
                    { type: 'string', min: 6, message: '密码不能少于6位。', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        loginSubmit(formName){
            console.log(this.$refs);
            console.log("HEYHEYHEY!")
            this.$refs[formName].validate((valid) => {
                setTimeout(() => {
                    if (valid) {
                        this.$Message.success(`登录成功！\n用户名：${this.loginForm.user}`);
                    } else {
                        this.$Message.error('请检查你的用户名与密码！');
                    }
                }, 1000);
            })
        }
    }
}
</script>

