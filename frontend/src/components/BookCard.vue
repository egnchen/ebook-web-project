<template>
<v-flex xs12 md8 class="margin-card">
    <v-card color="white" style="padding: 10px">
        <v-layout align-center>
            <v-flex xs3>
                <v-img
                    :src="'/' + book.img.src"
                    height="125px"
                    contain />
            </v-flex>
            <v-flex xs6>
            <v-card-title primary-title>
                <div>
                    <div class="headline">{{ book.title }}</div>
                    <div>{{ book.author }}</div>
                    <div>
                        <span>{{ book.publisher }}</span>
                        ISBN:<span>{{ book.ISBN || '1234567890' }}</span>
                    </div>
                    <div>库存：{{ book.stock || 100 }}</div>
                </div>
            </v-card-title>
            </v-flex>
            <v-flex xs3 class="card-action">
                <slot name="action" :idxData="idxData"></slot>
            </v-flex>
        </v-layout>
        <v-divider light></v-divider>
    </v-card>
</v-flex>
</template>

<style scoped>
.margin-card {
    margin: 0.25em;
}
</style>

<script>
export default {
    props: ['book', 'idxData']
}
</script>

