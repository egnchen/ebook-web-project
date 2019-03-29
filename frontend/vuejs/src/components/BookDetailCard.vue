<template>
<v-card>
    <v-card-title>
        <v-layout row wrap justify-space-between align-center>
            <v-flex xs5>
                <v-img
                    :src="'/' + book.img.src"
                    height="250px"
                    contain />
            </v-flex>
            <v-flex xs7>
                <div>
                    <div class="display-2">{{ book.title }}</div>
                    <div class="subheading">{{ book.author }}</div>
                </div>
            </v-flex>
            <v-flex xs12><div style="height:50px"></div></v-flex>
            <v-flex xs5 offset-xs1>
                <div>
                    评分:{{ book.rating }}
                    <v-rating style="display:inline"
                        v-model="book.rating"
                        dense half-increments hover />
                </div>
                <div>出版商：{{ book.publisher }}</div>
                <div>ISBN：{{ book.ISBN || '1234567890' }}</div>
                <div>库存：{{ book.stock || 100 }}</div>
            </v-flex>
            <v-flex xs6>
                {{ book.desc }}
            </v-flex>
        </v-layout>
    </v-card-title>
</v-card>
</template>

<script>
export default {
    props: ['book']
}
</script>
