<template>
<v-toolbar app color="primary" style="zIndex: 100">
    <v-toolbar-title class="headline">
        <v-hover>
            <span class="white--text">
                <v-icon large color="white">book</v-icon>
                <span class="font-weight-bold">E</span>
                <span class="font-weight-light">Book</span>
                <span class="font-weight-light">  图书集市</span>
            </span>
        </v-hover>
    </v-toolbar-title>
    
    <v-spacer></v-spacer>
    
    <v-tooltip bottom>
        <template v-slot:activator="{ on }">
            <v-btn icon to="/" v-on="on">
                <v-icon color="white">fas fa-home</v-icon>
            </v-btn>
        </template>
        <span>首页</span>
    </v-tooltip>
    <v-tooltip bottom>
        <template v-slot:activator="{ on }">
            <v-btn icon to="/cart" v-on="on">
                <v-icon color="white">fas fa-shopping-cart</v-icon>
            </v-btn>
        </template>
        <span>购物车</span>
    </v-tooltip>
    <v-tooltip bottom>
        <template v-slot:activator="{ on }">
            <v-btn icon to="/hot" v-on="on">
                <v-icon color="white">fas fa-fire</v-icon>
            </v-btn>
        </template>
        <span>时下流行</span>
    </v-tooltip>
    <v-tooltip v-if="this.$store.getters.user.username === undefined" bottom>
        <template v-slot:activator="{ on }">
            <v-btn icon to="/login" v-on="on">
                <v-icon color="white">fas fa-sign-in-alt</v-icon>
            </v-btn>
        </template>
        <span>登录</span>
    </v-tooltip>
    <v-tooltip v-else bottom>
        <template v-slot:activator="{ on }">
            <v-btn icon @click="logout" v-on="on">
                <v-icon color="white">fas fa-sign-out-alt</v-icon>
            </v-btn>
        </template>
        <span>登出</span>
    </v-tooltip>
</v-toolbar>
</template>

<script>
export default {
    methods: {
        logout() {
            // just delete the Json Web Token and user info
            this.$store.commit("removeJWT", this.$axios)
            this.$store.commit("removeUser")
            this.$store.commit("setPrompt", "已登出")
        }
    }
}
</script>
