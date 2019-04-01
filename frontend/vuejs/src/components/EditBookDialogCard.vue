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
                <v-text-field label="ISBN" required v-model="book.ISBN" />
            </v-flex>
            
            <v-flex xs12 md6 offset-md1>
                <v-text-field label="库存量" required v-model="book.stock" />
            </v-flex>
            <v-flex xs12>
                <v-textarea label="描述" required v-model="book.desc" />
            </v-flex>
        </v-layout>
    </v-card-text>
    <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="$emit('close-dialog')">保存</v-btn>
    </v-card-actions>
</v-card>
</template>

<script>
export default {
    props: ['book'],
    created(){
        this.book.ISBN = this.book.ISBN || 1234567890
        this.book.stock = this.book.stock || 100
    }
}
</script>

