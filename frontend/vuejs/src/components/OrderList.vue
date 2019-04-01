<template>
<v-data-table :headers="headers" :items="order" class="elevation-1" >
    <template v-slot:items="props">
        <td>{{ props.item.title }}</td>
        <td>
            <v-edit-dialog :return-value.sync="props.item.amount" lazy
                @save="save" @cancel="cancel" @open="open" @close="close" > 
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
</v-data-table>
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
            ]
        }
    }
}
</script>
