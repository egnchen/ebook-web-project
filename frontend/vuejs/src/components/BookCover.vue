<template>
<div class="book-cover" @mouseover="onMouseIn" @mouseout="onMouseOut" >
    <div class="book-cover-img-container"
        :style="{height: book.img.size[1]/book.img.size[0]*itemWidth + 'px'}">
        <img class="book-cover-img"  v-lazy="book.img.src"/>
    </div>
    <div class="book-cover-meta">
        <span class="book-cover-title">{{ book.title }}</span>
        <span class="book-cover-author">{{ book.author }}</span>
    </div>
</div>
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
    z-index: 50;
    margin: 0 auto;
    transform: scale(1.1);
    transition: opacity 0.25s linear, transform 0.25s cubic-bezier(0.075, 0.82, 0.165, 1);
}

.book-cover-img[lazy=loading] {
    max-width: 50%;
    opacity: 0.25;
}

.book-cover-img[lazy=loaded] {
    max-width: 100%;
}

.book-cover-img[lazy=error] {
    max-width: 50%;
}

.book-cover-meta {
    background-color: #dddddd;;
    position: relative;
    z-index: 100;
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

export default {
    props: ['book', 'itemWidth'],
    components: {
    },
    methods: {
        onMouseIn: function(event) {
            let target = event.currentTarget;
            let image = target.getElementsByClassName("book-cover-img")[0];
            let meta = target.getElementsByClassName("book-cover-meta")[0];
            let title = target.getElementsByClassName("book-cover-title")[0];
            let author = target.getElementsByClassName("book-cover-author")[0];
            image.style.transform = "scale(1.5)"
            image.style.filter = "blur(2px)"
            //meta.style.top = '-150px';
            meta.style.backgroundColor = "#555555a0"
            title.style.color = "white"
        },

        onMouseOut: function(event) {
            let target = event.currentTarget;
            let image = target.getElementsByClassName("book-cover-img")[0];
            let meta = target.getElementsByClassName("book-cover-meta")[0];
            let title = target.getElementsByClassName("book-cover-title")[0];
            let author = target.getElementsByClassName("book-cover-author")[0];
            image.style = ""
            meta.style.top = ""
            meta.style.backgroundColor = ""
            title.style = ""
            author.style = ""
        }
    },
    data() {
        return {
            image_url: 'CSAPP.jpg' // waited load
        }
    }
}
</script>
