<template>
<v-card>
    <v-card-title>
        <v-layout row wrap justify-space-between align-center>
            <v-flex xs5>
                <v-img
                    :src="'/' + book.picture.path"
                    height="250px"
                    contain />
            </v-flex>
            <v-flex xs7>
                <div>
                    <div class="display-2">{{ book.title }}</div>
                    <div class="subheading">{{ book.author }}</div>
                    <div>评分:{{ book.stars }}
                        <v-rating style="display:inline"
                            v-model="rating"
                            dense half-increments hover />
                    </div>
                    <div>出版商：{{ book.publisher }}</div>
                    <div>ISBN：{{ book.ISBN || '1234567890' }}</div>
                    <div>库存：{{ book.stock || "未知"}}</div>
                    
                    <div>简介: {{ book.description }}</div>
                </div>
            </v-flex>
        </v-layout>
    </v-card-title>
    <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn icon color="primary" class="white--text"><v-icon>add</v-icon></v-btn>
        <v-btn icon color="pink" class="white--text"><v-icon>favorite</v-icon></v-btn>
    </v-card-actions>
</v-card>
</template>

<script>
export default {
    props: ['bookId'],
    data() {
        return {
            book: {
                picture: {
                    path: "loading.gif"
                }
            }
        }
    },
    created() {
        let vm = this
        this.$axios.get("/book", {
            params: {
                bookId: this.bookId
            }
        })
        .then(response => {
            vm.book = response.data
        })
        .catch(error => {
            vm.$store.commit("setPrompt", `取回数据失败，error：${error}`)
        })
    },
    computed: {
        rating: {
            get() {
                return Math.round(this.book.stars) / 2
            },
            set(v) {
                this.book.stars = v * 2;
            }
        }
    }
}
</script>
