<template>
<v-app>
    <SideBar v-model="navVisibility"></SideBar> 
    <ToolBar />

    <v-content>
        <v-container fluid>
            <transition name="fade">
                <router-view></router-view>
            </transition>
        </v-container>
    </v-content>

    <v-snackbar v-model="snackBarVis" :top="true" :timeout="2000">
        {{ this.$store.getters.prompt }}
    </v-snackbar>
</v-app>
</template>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity .25s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>


<script>
import SideBar from './components/SideBar'
import ToolBar from './components/ToolBar'
export default {
    name: 'App',
    components: {
        SideBar,
        ToolBar
    },
    data () {
        return {
            navVisibility: true,
            navContent: [
                {icon: 'fas fa-book', title: '编程'}
            ]
        }
    },
    computed: {
        snackBarVis: {
            get() {
                return !!(this.$store.getters.prompt)
            },
            set() {
                this.$store.commit("setPrompt", "")
            }
        }
    }
}
</script>