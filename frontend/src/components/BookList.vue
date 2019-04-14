<template>
<!-- <v-layout row wrap>
    <BookCard v-for="(book,idx) in books" :key="idx" :book="book"/>
</v-layout> -->
<v-layout row wrap justify-center>
    <v-flex xs12>
        <v-text-field solo label="搜索" prepend-inner-icon="fas fa-search"
            @keyup.enter="bookSearch" v-model="searchString">
        </v-text-field>
    </v-flex>
    <div style="text-align:center" v-if="books_display.length === 0">
        {{ prompt }}
    </div>
    <div>
        <v-btn color="primary" @click="getBookList">获取图书数据</v-btn>
    </div>
    <v-flex xs12 md8>
        <v-breadcrumbs :items="path" divider=">" />
    </v-flex>
    <component :is="bookCardType"
        v-for="(book,idx) in books_display" :key="idx" :book="book"
        :idxData="idx" @delete-item="delItem"/>
</v-layout>
</template>

<script>
import UserBookCard from './UserBookCard'
import AdminBookCard from './AdminBookCard'
import CartBookCard from './CartBookCard'
import default_bookdata from '../data/books'

import axios from 'axios';

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
        },
        getBookList() {
            var vm = this;
            this.prompt = "正在取回数据"
            axios.get("/bookList")
            .then(function(response){
                console.log("book fetch success")
                console.log(response)
                vm.books_display = JSON.parse(response.data)
            })
            .catch(function(error){
                vm.prompt = "取回数据失败，错误：" + error
            })
        }
    },
    data() {
        return {
            searchString: '',
            books_display: [],
            bookCardType: '',
            prompt: "数据为空",
            path: [
                {
                    text: '游览',
                    disabled: true,
                    href: '/'
                },
                {
                    text: '所有图书',
                    disabled: false,
                    href: '/'
                }
            ]
        }
    },
    created() {
        var vm = this;
        
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