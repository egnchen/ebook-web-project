<template>
<BookCard :book="book" >
    <template v-slot="props">
        <div class="rating-section">
            <span class="rating">评分 {{ props.book.score }}/10</span>
            <v-rating v-model="book_rating" half-increments readonly hover
            style="display:inline" dense background-color="white" color="blue accent-1" />
            <span class="caption" style="color:grey">评分数据来自豆瓣</span>
        </div>
    </template>
    <template v-slot:action="props">
        <div class="actions">
            <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                    <v-btn icon dark color="primary" v-on="on"
                        @click="addToCart(book.id)">
                        <v-icon dark>add</v-icon>
                    </v-btn>
                </template>
                <span>添加到购物车</span>
            </v-tooltip>
            <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                    <v-btn icon dark color="pink" v-on="on" to="/">
                        <v-icon dark>favorite</v-icon>
                    </v-btn>
                </template>
                <span>喜欢</span>
            </v-tooltip>
            <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                    <v-btn icon dark color="teal"
                        v-on="on" @click.stop="$emit('show-dialog', props.book.id)">
                        <v-icon dark>list</v-icon>
                    </v-btn>
                </template>
                <span>查看详情</span>
            </v-tooltip>
        </div>
    </template>
</BookCard>
</template>

<style scoped>
.rating {
    font-size: 20px;
    text-align: center;
    display: block;
}

.rating-section {
    display: flex;
    flex-flow: column wrap;
    align-items: center;
    justify-content: center;
    height: 100%;
}

.actions>button {
    margin: 0px 10px;
}

</style>

<script>
    import BookCard from './BookCard'
    import BookDetailCard from './BookDetailCard'

    export default {
    props: ['book', 'idxData'],
    components: {BookCard, BookDetailCard},
    computed: {
        book_rating() {
            return Math.round(this.book.score) / 2;
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
