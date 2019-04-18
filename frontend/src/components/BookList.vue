<template>
<!-- <v-layout row wrap>
    <BookCard v-for="(book,idx) in books" :key="idx" :book="book"/>
</v-layout> -->
<v-layout row wrap justify-center>
    <v-flex xs12>
        <v-text-field solo label="搜索" prepend-inner-icon="fas fa-search"
            @keyup.enter="getBookList" v-model="search_string">
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
    
    <v-flex xs12 md8>
        <div class="text-xs-center">
        <v-pagination v-model="page" @input="getBookList"
            :length="page_count" :total-visible="7" />
        </div>
    </v-flex>
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
        getBookList() {
            var vm = this;
            this.prompt = "正在取回数据"
            var req_params = {
                pageNumber: this.page
            }
            if(this.search_string)
                req_params.bookTitle = this.search_string
            axios.get("http://localhost:8080/api/books", {
                params: req_params
            })
            .then(function(response){
                vm.books_display = response.data.content
                vm.page = response.data.pageNumber + 1
                vm.page_count = response.data.totalPages
            })
            .catch(function(error){
                vm.prompt = "取回数据失败，错误：" + error
            })
        },
        delItem(key){
            console.log(key)
            this.books_display.splice(key,1)
        }
    },
    data() {
        return {
            search_string: '',
            books_display: [],
            bookCardType: '',
            page: 1,
            page_count: 1,
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