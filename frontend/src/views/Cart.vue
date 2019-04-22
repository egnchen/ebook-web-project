<template>
<v-card>
    <v-card-title>
        购物车
        <v-spacer></v-spacer>
        <v-text-field
            v-model="searchString"
            append-icon="fas fa-search" label="搜索"
            single-line hide-details />
    </v-card-title>
    <v-data-table :headers="headers" :items="order" class="elevation-1" :search="searchString">
        <template v-slot:items="props">
            <td>{{ props.item.book.title }}</td>
            <td>
                <v-edit-dialog
                    :return-value.sync="props.item.amount"
                    @save="updateOrderItem(props.item)" lazy> 
                    {{ props.item.amount }}
                    <template v-slot:input>
                        <v-text-field
                        v-model="props.item.amount"
                        type="number" label="编辑数量"
                        single-line counter lazy/>
                    </template>
                </v-edit-dialog>
            </td>
        </template>
        <template v-slot:no-results>
            <v-alert :value="true" color="error" icon="fas fa-warning">
            Your search for "{{ searchString }}" found no results.
            </v-alert>
        </template>
    </v-data-table>
</v-card>
</template>

<style>

</style>

<script>

export default {
    data() {
        return {
            order: [],
            headers: [

            ],
            searchString: ""
        }
    },
    methods: {
        updateOrderItem(orderItem) {
            this.$axios.put("/cart", orderItem)
            .then(response => {
                console.log("Success, " + response)
            })
            .catch(error => {
                console.log("Failed, " + error)
            })
        }
    },
    created() {
        let vm = this
        this.$axios.get("/cart")
        .then((response) => {
            vm.order = response.data.content
        })
        .catch((error) => {
            console.log("fetch data failed, " + error)
        })
    }
}
</script>
