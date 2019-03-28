<template>
<v-app>
    <v-navigation-drawer app color="orange darken-1" v-model="navVisibility">
        <SideBar style="margin-top: 64px"> </SideBar>
    </v-navigation-drawer> 
    
    <v-toolbar app color="primary" style="zIndex: 100">
        <v-toolbar-title class="headline" @click="navVisibility = !navVisibility">
            <v-hover>
                <span class="white--text">
                    <v-icon large color="white">book</v-icon>
                    <span class="font-weight-bold">E</span>
                    <span class="font-weight-light">Book</span>
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
        <v-tooltip bottom>
            <template v-slot:activator="{ on }">
                <v-btn icon to="/login" v-on="on">
                    <v-icon color="white">fas fa-sign-in-alt</v-icon>
                </v-btn>
            </template>
            <span>登录</span>
        </v-tooltip>
    </v-toolbar>

    <v-content>
        <v-container fluid>
            <router-view></router-view>
        </v-container>
    </v-content>
</v-app>
</template>

<script>
import SideBar from './components/SideBar'

export default {
    name: 'App',
    components: {
        SideBar
    },
    data () {
        return {
            navVisibility: true,
            navContent: [
                {icon: 'fas fa-book', title: '编程'}
            ]
        }
    }
}
</script>