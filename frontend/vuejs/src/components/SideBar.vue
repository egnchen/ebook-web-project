<template>
<v-toolbar flat class="transparent">
    <v-list class="pa-0">
        <v-list-tile>
            <v-list-tile-avatar>
                <img src="/static/user.svg">
            </v-list-tile-avatar>
            <v-list-tile-content>
                <v-list-tile-title>{{ "爸爸" }}</v-list-tile-title>
            </v-list-tile-content>
        </v-list-tile>
    </v-list>

    <v-list>
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
</v-toolbar>
</template>

<style scoped>

</style>

<script>
export default {
    data(){
        return {
            navContent: [
                {
                    title: "修改库存",
                    icon: "fas fa-edit",
                    path: "/admin/edit-stock"
                }
            ]
        }
    }
}
</script>

