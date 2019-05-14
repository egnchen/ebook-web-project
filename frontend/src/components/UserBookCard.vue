<template>
<BookCard :book="book" @click="$emit('show-dialog', book.id)">
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
                <!-- detail button - invoke dialog -->
                <v-btn icon dark color="teal" @click.stop="$emit('show-dialog', book.id)">
                    <v-icon dark>list</v-icon>
                </v-btn>
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
            let vm = this
            this.$axios.post("/cart", {
                "bookId": bookId
            })
            .then((response) => {
                vm.$store.commit("setPrompt", `添加成功`)
            })
            .catch((error) => {
                let resp = error.response
                vm.$store.commit("setPrompt", `添加失败，${resp.data ? resp.data.status + ' ' + resp.data.message : error}`)
            })
        }
    }
}
</script>
