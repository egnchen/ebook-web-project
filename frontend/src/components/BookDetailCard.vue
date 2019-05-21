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
                    <div>
                        <span class="prefix">评分:</span>
                        {{ book.score }}
                        <v-rating style="display:inline"
                            v-model="rating"
                            dense half-increments hover />
                    </div>
                    <div>
                        <span class="prefix">出版商：</span>
                        {{ book.publisher }}
                    </div>
                    <div>
                        <span class="prefix">ISBN：</span>
                        {{ book.isbn || "未知" }}
                    </div>
                    <div>
                        <span class="prefix">库存：</span>
                        {{ book.stock || "未知" }}
                    </div>
                    <div><span class="prefix">简介:</span>
                        <br />
                        {{ book.description }}
                    </div>
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

<style scoped>
.prefix {
    color: grey
}

</style>

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
    watch: {
        bookId(newVal) {
            if(newVal > 0) {
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
            }
        }
    },
    computed: {
        rating(){
            return Math.round(this.book.score) / 2;
        }
    }
}
</script>
