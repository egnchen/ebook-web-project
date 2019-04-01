<template>
<v-navigation-drawer v-model="value" style="margin-top: 64px" app flat>
    <v-toolbar flat>
        <v-list class="pa-0">
            <v-list-tile avatar>
                <v-list-tile-avatar>
                    <img src="/static/user.svg">
                </v-list-tile-avatar>
                <v-list-tile-content>
                    <v-list-tile-title>{{ "管理员爸爸" }}</v-list-tile-title>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
    </v-toolbar>
    
    <v-divider></v-divider>

    <v-list dense>
        <v-list-tile
            v-for="(item, idx) in this.navContent"
            :key="idx"
            @click="$router.push(item.path)">
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
                    icon: "fas fa"
                }
                {
                    title: "修改库存",
                    icon: "fas fa-edit",
                    path: "/admin/edit-stock"
                },
                {
                    title: "我的订单",
                    icon: "fas fa-stream",
                    path: "/order"
                },
                {
                    title: "统计信息",
                    icon: "fas fa-asterisk",
                    path: "/admin/stats"
                }
            ]
        }
    }
}
</script>

