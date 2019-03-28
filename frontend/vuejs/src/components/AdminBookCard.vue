<template>
<BookCard :book="book">
    <template v-slot:action>
        <span>库存量</span>
        <v-text-field type="number" v-model="stock" />
    </template>
</BookCard>
</template>

<style scoped>
.card-score {
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-items: flex-end;
}

.card-score span {
    display: block
}
</style>

<script>
import BookCard from './BookCard'
export default {
    props: ['book', 'idxData'],
    components: {BookCard},
    data() {
        return {
            stock: 0
        }
    },
    watch: {
        stock() { this.book.stock = parseInt(this.stock); }
    }
}
</script>