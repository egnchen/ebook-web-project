<template>
<BookCard :book="book" :idxData="idxData">
    <template v-slot:action="bookProps">
        <span>数量</span>
        <v-text-field type="number" v-model="count" />
        <v-btn icon @click="$emit('delete-item', bookProps.idxData)">
            <v-icon>fas fa-minus-square</v-icon>
        </v-btn>
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
            count: 1
        }
    },
    watch: {
        stock() { this.book.stock = parseInt(this.stock); }
    }
}
</script>