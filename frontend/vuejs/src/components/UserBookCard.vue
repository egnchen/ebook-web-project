<template>
<BookCard :book="book">
    <template v-slot:action="bookProps">
        <div class="action-container">
            <span>评分</span>
            <v-rating v-model="book.score" dense hover half-increments />
            <span class="black--text">
                <v-btn icon dark color="primary" to="{name: 'buy', params: { id: bookProps.idxData }}">
                    <v-icon dark>add</v-icon>
                </v-btn>
                <v-btn icon dark color="pink" to="/">
                    <v-icon dark>favorite</v-icon>
                </v-btn>
                <v-dialog max-width="800px">
                    <template v-slot:activator="{ on }">
                        <v-btn icon dark color="teal" v-on="on">
                            <v-icon dark>list</v-icon>
                        </v-btn>
                    </template>
                    <BookDetailCard :book="book" />
                </v-dialog>
            </span>
        </div>
    </template>
</BookCard>
</template>

<style scoped>
.action-contianer {
    height: 100%;
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-items: center;
}

.action-container span {
    display: block
}
</style>

<script>
import BookCard from './BookCard'
import BookDetailCard from './BookDetailCard'
export default {
    props: ['book', 'idxData'],
    components: {BookCard, BookDetailCard}
}
</script>
