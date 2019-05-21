<template>
<v-flex xs12 md8 class="margin-card">
    <v-card color="white" style="padding: 10px">
        <v-layout align-center>
            <v-flex xs3>
                <v-img
                    :src="'/' + book.picture.path"
                    style="margin: 10px; max-height: 200px;"
                    contain />
            </v-flex>
            <v-flex xs9>
                <v-layout row wrap>
                    <v-flex xs8>
                        <v-card-title primary-title>
                            <div>
                                <div class="headline">{{ book.title }}</div>
                                <div>{{ book.author }}</div>
                                <div class="book-info">
                                    <span>{{ book.publisher }}</span>
                                    <span>ISBN：{{ book.isbn || "未知"}}</span>
                                    <span>库存：{{ book.stock || "未知" }}</span>
                                </div>
                            </div>
                        </v-card-title>
                    </v-flex>
                    <v-flex xs4>
                        <slot :book="book"></slot>
                    </v-flex>
                </v-layout>
                <v-divider />
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <slot name="action" :book="book"></slot>
                </v-card-actions>
            </v-flex>
        </v-layout>
    </v-card>
</v-flex>
</template>

<style scoped>
.margin-card {
    margin: 0.25em;
}
.book-info span {
    display: block;
}
</style>

<script>
export default {
    props: ['book', 'idxData'],
    computed: {
        book_rating(){
            return Math.round(this.book.score) / 2
        }
    }
}
</script>

