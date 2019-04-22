<template>
<BookCard :book="book">
    <template v-slot:action="bookProps">
        <div class="action-container">
            <span>评分 {{ book.stars }}</span>
            <v-rating v-model="rating" dense hover half-increments readonly />
            <span class="black--text">
                <v-btn icon dark color="primary" @click="addToCart(book.id)">
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
.action-container {
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
    components: {BookCard, BookDetailCard},
    computed: {
        rating() {
            return Math.round(this.book.stars) / 2;
        }
    },
    methods: {
        addToCart(bookId) {
            this.$axios.post("/cart", {
                "bookId": bookId
            })
            .then((response) => {
                console.log("add succeed, " + response)
            })
            .catch((error) => {
                console.log("add failed, " + error)
            })
        }
    }
}
</script>
