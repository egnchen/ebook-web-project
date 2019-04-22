<template>
<v-card>
    <v-card-title>
        <span class="headline">
            <v-icon>fas fa-edit</v-icon>
            编辑
        </span>
    </v-card-title>
    <v-card-text>
        <v-layout wrap>
            <v-flex xs12>
                <v-text-field label="标题" required v-model="book.title" />
            </v-flex>
            <v-flex xs12 md5>
                <v-text-field label="作者" required v-model="book.author" />
            </v-flex>
            <v-flex xs12 md6 offset-md1>
                <v-text-field label="出版社" required v-model="book.publisher" />
            </v-flex>
            <v-flex xs12 md5>
                <v-text-field label="ISBN" required v-model="book.isbn" />
            </v-flex>
            
            <v-flex xs12 md6 offset-md1>
                <v-text-field label="库存量" required v-model="book.stock" />
            </v-flex>
            <v-flex xs12>
                <v-textarea label="描述" required v-model="book.description" />
            </v-flex>
        </v-layout>
    </v-card-text>
    <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="saveBook">保存</v-btn>
    </v-card-actions>
</v-card>
</template>

<script>
import axios from 'axios';

export default {
    props: ['book'],
    created() {
        console.log(this.book);
    },
    methods: {
        saveBook() {
            axios.put("/book", this.book)
            .then(function(response){
                console.log("Success!" + response)
            })
            .catch(function(error){
                console.log("Failed!" + error)
            })
            this.$emit("close-dialog")
        }
    }
}
</script>

