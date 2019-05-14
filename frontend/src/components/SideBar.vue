<template>
<v-navigation-drawer style="margin-top: 64px" app flat>
    <v-toolbar flat>
        <v-list class="pa-0">
            <v-list-tile avatar>
                <v-list-tile-avatar>
                    <img src="/static/user.svg">
                </v-list-tile-avatar>
                <v-list-tile-content>
                    <v-list-tile-title>{{ this.$store.getters.user.username || "未登录" }}</v-list-tile-title>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
    </v-toolbar>
    
    <v-divider></v-divider>

    <v-list dense>
        <v-list-tile
            v-for="(item, idx) in this.navContent"
            :key="idx"
            @click="$router.push({name: item.pathName })">
            <v-list-tile-action>
                <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
                <v-list-tile-title>{{ item.title }}</v-list-tile-title>
            </v-list-tile-content>
        </v-list-tile>
    </v-list>
</v-navigation-drawer>
</template>

<style scoped>

</style>

<script>
export default {
    props: ['value'],
    data(){
        return {
            navContent: [
                {
                    title: "瀑布流视图",
                    icon: "fas fa-stream",
                    pathName: "bookflow"
                },
                {
                    title: "修改库存",
                    icon: "fas fa-edit",
                    pathName: "admin-edit-stock"
                },
                {
                    title: "我的订单",
                    icon: "fas fa-list-alt",
                    pathName: "order"
                },
                {
                    title: "统计信息",
                    icon: "fas fa-asterisk",
                    pathName: "admin-view-stats"
                }
            ]
        }
    },
    created() {
        if(localStorage.getItem("JWT") !== null) {
            // logged in, get user profile
            let vm = this
            this.$axios.get("/profile")
                .then((response) => {
                    vm.$store.commit("setUser", response.data)
                })
                .catch((error) => {
                    vm.$store.commit("invalidateUser")
                })
        }
    }
}
</script>

