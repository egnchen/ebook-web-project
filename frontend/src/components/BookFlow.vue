<template>
<div class="waterfall-container" transition="slide-y-transition">
    <Waterfall :maxCol='maxColumn' :minCol='minColumn' :gutterWidth='gutterWidth' :gutterHeight='gutterWidth + 5'
        :fixWidth='0'>
        <WaterfallItem :width='itemWidth' v-for="(item, index) in books" :key="index">
            <BookCover :book="item" :key="index" :itemWidth="250"> </BookCover>
        </WaterfallItem>
    </Waterfall>
</div>
</template>

<style scoped>
.waterfall-container {
    margin: 10px;
}
</style>
<script>
import { Waterfall, WaterfallItem } from 'vue2-waterfall'
import BookCover from './BookCover'
// import default_book_data from '../data/books'

export default {
    components: {
        Waterfall, WaterfallItem, BookCover
    },
    props: {
        minColumn: {
            required: false,
            default: function() {return 2;}
        }, 
        maxColumn: {
            required: false,
            default: function() {return 7;}
        },
        itemWidth: {
            required: false,
            default: function() {return 250;}
        },
        gutterWidth: {
            required: false,
            default: function() {return 15;}
        }
    },
    data() {
        return {
            books: []
        }
    },
    created() {
        this.$axios.get("/books")
        .then(response => {
            this.books = response.data.content
        })
        .catch(error => {
            this.$store.commit("setPrompt", `错误，${error}`)
        })
    }
}
</script>
