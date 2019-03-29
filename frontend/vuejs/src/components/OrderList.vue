<template>
<v-data-table :headers="headers" :items="order" class="elevation-1" >
    <template v-slot:items="props">
        <td>{{ props.item.title }}</td>
        <td>{{ props.item.amount }}</td>
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
