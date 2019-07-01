<template>
<v-navigation-drawer style="margin-top: 64px" app flat>
    <v-toolbar flat>
        <v-list class="pa-0">
            <v-list-tile avatar>
                <v-list-tile-avatar>
                    <img src="/static/user.svg">
                </v-list-tile-avatar>
                <v-list-tile-content>
                    <v-list-tile-title>{{ this.$store.getters.user.username || "Please login" }}</v-list-tile-title>
                    <v-list-tile-sub-title>Role: {{ roleName }}</v-list-tile-sub-title>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
    </v-toolbar>
    
    <v-divider></v-divider>

    <v-list dense>
        <v-list-tile
                v-for="(item, idx) in this.ordNavContent"
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
    <v-divider></v-divider>
    <v-list dense v-if="roleName === 'admin'">
        <v-list-tile
                v-for="(item, idx) in this.adminNavContent"
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
    data() {
        return {
            ordNavContent: [
                {
                    title: "瀑布流视图",
                    icon: "fas fa-stream",
                    pathName: "bookflow"
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
            ],
            adminNavContent: [
                {
                    title: "库存管理",
                    icon: "fas fa-edit",
                    pathName: "admin-edit-stock"
                },
                {
                    title: "所有订单",
                    icon: "fas fa-list-alt",
                    pathName: "admin-all-orders"
                },
                {
                    title: "用户管理",
                    icon: "fas fa-users",
                    pathName: "admin-manage-users"
                }
            ]
        }
    },
    computed: {
        roleName() {
            let mapper = {
                "ROLE_ADMIN": 'admin',
                "ROLE_USER": 'user'
            };
            return mapper[this.$store.state.user.role];
        }
    },
    created() {
        if(localStorage.getItem("JWT") !== null) {
            // previously logged in, get user profile
            let vm = this
            this.$axios.get("/profile")
                .then((response) => {
                    if(response.data.statusCodeValue === 403) {
                        // failed
                        vm.$store.commit("removeJWT")
                        vm.$store.commit("invalidateUser")
                        vm.$store.commit("setPrompt", response.data.body)
                    } else {
                        vm.$store.commit("setUser", response.data)
                        vm.$store.commit("setSuccessPrompt", "Welcome back, " + response.data.username)
                    }
                })
                .catch((error) => {
                    vm.$store.commit("invalidateUser")
                    vm.$store.commit("setErrorPrompt", "Something went wrong when trying to login again:" + error)
                })
        }
    }
}
</script>

