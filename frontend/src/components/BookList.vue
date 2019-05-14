<template>
<v-layout row wrap justify-center @>
    <!-- search bar -->
    <v-flex xs12>
        <v-text-field solo label="搜索" prepend-inner-icon="fas fa-search"
            @keyup.enter="getBookList" v-model="search_string">
        </v-text-field>
    </v-flex>
    <!-- empty list prompt -->
    <div style="text-align:center" v-if="books_display.length === 0">
        {{ prompt }}
    </div>
    <!-- refresh list button -->
    <div>
        <v-btn color="primary" @click="getBookList">刷新列表</v-btn>
    </div>
    <!-- path breadcrumb -->
    <v-flex xs12 md8>
        <v-breadcrumbs :items="path" divider=">" />
    </v-flex>
    <!-- list items -->
    <!-- wrap a public dialog outside list items-->
    <component :is="bookCardType"
        v-for="(book,idx) in books_display" :key="idx" :book="book"
        :idxData="idx" @delete-item="delItem" @show-dialog="showDialog" />
    <v-dialog v-model="show_dialog" max-width="800px">
        <BookDetailCard :bookId="dialog_book_id"/>
    </v-dialog>
    <!-- pagination -->
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
    import default_bookdata from '../data/books'
    import BookDetailCard from './BookDetailCard'

    export default {
    props: {
        books: {
            type: Array,
            default: () => default_bookdata.book_list
        }
    },
    methods: {
        getBookList() {
            let vm = this;
            this.prompt = "正在取回数据"
            let req_params = {}
            if(this.search_string) {
                req_params.bookTitle = this.search_string
                req_params.pageNumber = 1
            } else
                req_params.pageNumber = this.page
            
            var url = "/books"
            this.prompt = "取回数据中"
            this.$axios.get(url, {
                params: req_params
            })
            .then(function(response){
                vm.books_display = response.data.content
                vm.page = response.data.pageable.pageNumber + 1
                vm.page_count = response.data.totalPages
                vm.prompt = "数据为空"
            })
            .catch(function(error){
                vm.prompt = "取回数据失败，错误：" + error
            })
        },
        showDialog(book_id) {
            this.dialog_book_id = book_id
            this.show_dialog = true;
        },
        delItem(key) {
            console.log(key)
            this.books_display.splice(key,1)
        }
    },
    data() {
        return {
            search_string: '',
            books_display: [],
            page: 1,
            page_count: 1,
            prompt: "数据为空",
            show_dialog: false,
            dialog_book_id: -1,
            bookCardType: '',
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
        else
            this.bookCardType = 'UserBookCard'
        this.getBookList()
    },
    components: {
        UserBookCard,
        AdminBookCard,
        BookDetailCard
    }
}
</script>