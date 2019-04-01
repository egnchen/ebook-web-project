<template>
<v-card>
    <v-card-title>
        所有订单
        <v-spacer></v-spacer>
        <v-text-field
            v-model="searchString" append-icon="fas fa-search" label="搜索"
            single-line hide-details />
    </v-card-title>
    <v-data-table :headers="headers" :items="order" class="elevation-1" :search="searchString">
        <template v-slot:items="props">
            <td>{{ props.item.title }}</td>
            <td>
                <v-edit-dialog :return-value.sync="props.item.amount" lazy> 
                    {{ props.item.amount }}
                    <template v-slot:input>
                        <v-text-field
                        v-model="props.item.amount"
                        type="number" label="编辑"
                        single-line counter />
                    </template>
                </v-edit-dialog>
            </td>
        </template>
        <v-alert v-slot:no-results :value="true" color="error" icon="fas fa-warning">
          Your search for "{{ searchString }}" found no results.
        </v-alert>
    </v-data-table>
</v-card>
</template>

<script>
export default {
    data() {
        return {
            order: [
                {
                    title: 'bookA',
                    amount: 1
                },
                {
                    title: 'bookB',
                    amount: 2
                },
                {
                    title: 'bookC',
                    amount: 1
                },
                {
                    title: 'bookD',
                    amount: 2
                },
                {
                    title: 'bookE',
                    amount: 1
                },
                {
                    title: 'bookF',
                    amount: 2
                }
            ],
            headers: [
                {
                    text: '书名',
                    value: 'title'
                },
                {
                    text: '数目',
                    value: 'amount'
                }
            ],
            searchString: ''
        }
    }
}
</script>
