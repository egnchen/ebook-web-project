<template>
<v-card class="book-cover">
    <div class="book-cover-img-container"
        :style="{height: book.picture.sizeH/book.picture.sizeW*itemWidth + 'px'}">
        <v-dialog max-width="800px">
            <template v-slot:activator="{ on }">
                <v-img class="book-cover-img" :alt="'public/static/book.svg'"
                    :src="book.picture.path" v-on="on">
                </v-img>
            </template>
            <BookDetailCard :book="book" />
        </v-dialog>
    </div>
    <div class="book-cover-meta">
        <span class="book-cover-title">{{ book.title }}</span>
        <span class="book-cover-author">{{ book.author }}</span>
    </div>
</v-card>
</template>

<style scoped>

.book-cover {
    width: 100%;
    padding: 3px;
    position: relative;
    min-height: 200px;
    border-radius: 5px;
}

.book-cover-img-container {
    overflow: hidden;
}

.book-cover-img {
    width: auto;
    height: auto;
    margin: 0 auto;
    transform: scale(1.1);
    max-width: 100%;
    transition: opacity 0.25s linear, transform 0.25s cubic-bezier(0.075, 0.82, 0.165, 1);
}

.book-cover-img:hover {
    transform: scale(1.4);
    filter: blur(2px);
}

.book-cover-meta {
    left: 0px;
    top: 0px;
    width: 100%;
    padding-left: 1em;
    text-align: left;
    transition: 0.25s cubic-bezier(0.075, 0.82, 0.165, 1);
}

.book-cover-meta > span {
    display: block;
}
.book-cover-title {
    font-size: 1.5em;
    color: black;
}

.book-cover-author {
    font-size: 1em;
    color: #bbbbbb;
}
</style>

<script>

import BookDetailCard from './BookDetailCard'

export default {
    components: { BookDetailCard },
    props: ['book', 'itemWidth'],
}
</script>
