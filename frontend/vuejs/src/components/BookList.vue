<template>
<!-- <v-layout row wrap>
    <BookCard v-for="(book,idx) in books" :key="idx" :book="book"/>
</v-layout> -->
<div>
<v-flex sm12>
    <v-text-field solo label="搜索" prepend-inner-icon="fas fa-search"
        @keyup.enter="bookSearch" v-model="searchString">
    </v-text-field>
</v-flex>
<div style="text-align:center" v-if="books_display.length === 0">
    结果为空
</div>
<v-flex>
    <component xs-12 :is="bookCardType"
        v-for="(book,idx) in books_display" :key="idx" :book="book"
        :idxData="idx" @delete-item="delItem"/>
</v-flex>
</div>
</template>

<script>
import UserBookCard from './UserBookCard'
import AdminBookCard from './AdminBookCard'
import CartBookCard from './CartBookCard'
import default_bookdata from '../data/books'

export default {
    props: {
        books: {
            type: Array,
            default: function() {
                return default_bookdata.book_list;
            }
        }
    },
    methods: {
        bookSearch(){
            var regex = RegExp(this.searchString, 'i') // not case sensitive
            this.books_display = this.books.filter(book => !!regex.exec(book.title))
        },
        delItem(key){
            console.log(key)
            this.books_display.splice(key,1)
        }
    },
    data() {
        return {
            searchString: '',
            books_display: [],
            bookCardType: ''
        }
    },
    created() {
        this.books_display = this.books
        // choose book card type
        if(this.$route.name === 'admin-edit-stock')
            this.bookCardType = 'AdminBookCard'
        else if(this.$route.name === 'cart')
            this.bookCardType = 'CartBookCard'
        else
            this.bookCardType = 'UserBookCard'
    },
    components: {UserBookCard, AdminBookCard, CartBookCard}
}
</script>