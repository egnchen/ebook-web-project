<template>
<v-card>
    <v-card-title>
        <v-spacer></v-spacer>
        <v-text-field
                v-model="searchTitle" append-icon="fas fa-search" label="点击书籍搜索"
                single-line readonly hide-details @click="defineSearch(null)" />
    </v-card-title>
    <v-data-table :headers="headers" :items="order" class="elevation-1">
        <template v-slot:items="props">
            <tr @click="props.expanded = !props.expanded">
                <td>{{ props.item.updateTime }}</td>
                <td>{{ localizedStatusPrompt(props.item) }}</td>
            </tr>
        </template>
        <template v-slot:expand="props">
            <v-list>
                <v-list-tile v-for="itm in props.item.orderItems"
                    :key="itm.book.id" avatar @click="defineSearch(itm.book)" >
                    <v-list-tile-avatar>
                        <img :src="itm.book.picture.path" />
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <v-list-tile-title v-text="itm.book.title"></v-list-tile-title>
                        <span color="grey">数量 {{ itm.amount }}</span>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </template>
        <v-alert v-slot:no-results :value="true" color="error" icon="fas fa-warning">
          Your search for "{{ searchTitle }}" found no results.
        </v-alert>
    </v-data-table>
</v-card>
</template>

<script>
export default {
    data() {
        return {
            order: [],
            headers: [
                {
                    text: '日期',
                    value: 'updateTime'
                },
                {
                    text: '状态',
                    value: 'status'
                }
            ],
            searchBook:  null
        }
    },
    watch: {
        searchBook(newVal) {
            let vm = this
            if (this.$route.name === "admin-all-orders") {
                if(newVal !== null)
                    this.$axios.get("stats/orders/by-book", { params: { bookId: newVal.id }})
                    .then(response => { vm.order = response.data })
                    .catch(error => { vm.$store.commit("setErrorPrompt", "Error occurred while requesting data.") })
                else
                    this.$axios.get("stats/orders")
                    .then(response => { vm.order = response.data })
                    .catch(error => { vm.$store.commit("setErrorPrompt", "Error occurred while requesting data.") })
            } else {
                this.$store.commit("setErrorPrompt", "Search not supported here.")
            }
        }
    },
    created() {
        let vm = this
        let url
        if (this.$route.name === "order")
            url = "/orders"
        else
            url = "/stats/orders"
        this.$axios.get(url)
        .then(response => {
            if (response.data.content)
                vm.order = response.data.content
            else
                vm.order = response.data
        })
        .catch(error => {
            vm.$state.commit("setPrompt", `error, ${error}`)
        })
    },
    methods: {
        localizedStatusPrompt(ord) {
            console.log(ord)
            let mapper = { submitted: "已提交" }
            return mapper[ord.status] || "未提交"
        },
        defineSearch(book) {
            this.searchBook = book
        }
    },
    computed: {
        searchTitle(){
            return this.searchBook ? this.searchBook.title : ""
        }
    }
}
</script>
