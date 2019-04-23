<template>
<BookCard :book="book" :idxData="idxData">
    <template v-slot:action="bookProps">
        <div class="action-container">
            <span>数量：<v-text-field type="number" v-model="count" class="amount-text-field"/></span>
            <v-btn color="error" @click="$emit('delete-item', bookProps.idxData)">
                <v-icon>fas fa-minus-square</v-icon>删除
            </v-btn>
        </div>
    </template>
</BookCard>
</template>

<style scoped>
.action-container {
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-items: center;
}

.amount-text-field {
    display: inline-block;
}
</style>

<script>
import axios from 'axios'
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