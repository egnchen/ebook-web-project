<template>
<BookCard :book="book">
    <template v-slot:action="bookProps">
        <span>评分</span>
        <span>
            <v-icon>star_border</v-icon>
            <v-icon>star_border</v-icon>
            <v-icon>star_border</v-icon>
            <v-icon>star_border</v-icon>
            <v-icon>star_border</v-icon>
        </span>
        <span class="black--text">
            <v-btn icon dark color="primary" to="{name: 'buy', params: { id: bookProps.idxData }}">
                <v-icon dark>add</v-icon>
            </v-btn>
            <v-btn icon dark color="pink" to="/">
                <v-icon dark>favorite</v-icon>
            </v-btn>
            <v-btn icon dark color="teal" to="/detail">
                <v-icon dark>list</v-icon>
            </v-btn>
        </span>
    </template>
</BookCard>
</template>

<style scoped>
.card-score {
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-items: flex-end;
}

.card-score span {
    display: block
}
</style>

<script>
import BookCard from './BookCard'
export default {
    props: ['book', 'idxData'],
    components: {BookCard}
}
</script>
