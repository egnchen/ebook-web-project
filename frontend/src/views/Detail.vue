<template>
<div class="app">
    <Card class="content-wrapper">
        <div class="book-cover">
            <img :src="book.img.src" />
        </div>
        <div class="book-meta">
            <span class="book-meta-title">{{ book.title }}</span>
            <span class="book-meta-author">{{ book.author }}</span>
            <span class="book-meta-stars">{{ book.stars }}</span>
            <span class="book-meta-publisher">{{ book.publisher }}</span>
            <p class="book-meta-description">{{ book.desc }}</p>
        </div>
    </Card>
</div>
</template>

<style scoped>
.content-wrapper {
    width: 80%;
    height: 400px;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-around;
    margin: 50px auto 0px;
    padding: 0 20px;
}

.book-cover {
    position: relative;
    top: 0px;
    display: block;
    float: left;
    height: 350px;
    width: 400px;
}


.book-cover img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    transition: 0.3s cubic-bezier(0.075, 0.82, 0.165, 1);
}

.book-cover img:hover {
    transform: scale(1.1);
    box-shadow: 0px 10px 20px #444444;
}


.book-meta {
    display: block;
    float: right;
    max-width: 40vw;
}

.book-meta span {
    display: block;
}

.book-meta-title {
    font-size: 3em;
}

.book-meta-author {
    font-size: 1.5em;
}

.book-meta-starts {
    font-size: 0.5em;
}

.book-meta-publisher {
    font-size: 1em;
}

.book-meta-description {
    font-size: 0.75em;
    color: #aaaaaa;
}
</style>

<script>
export default {
    props: {
        book: {
            required: false,
            default: function() {
                return {
                    "title": "Python\u7f16\u7a0b",
                    "author": "[\u7f8e]\u57c3\u91cc\u514b\u00b7\u9a6c\u745f\u65af , \u8881\u56fd\u5fe0 ",
                    "img": { "src": "images/948772e96bd7d85063de23804d942d33f8fe64d0.jpeg", "size": [540, 677] },
                    "publisher": " \u4eba\u6c11\u90ae\u7535\u51fa\u7248\u793e ",
                    "publish_date": " 2016-7-1 ",
                    "price": " CNY 89.00",
                    "stars": "9.1",
                    "desc": "\u672c\u4e66\u662f\u4e00\u672c\u9488\u5bf9\u6240\u6709\u5c42\u6b21\u7684Python \u8bfb\u8005\u800c\u4f5c\u7684Python \u5165\u95e8\u4e66\u3002\u5168\u4e66\u5206\u4e24\u90e8\u5206\uff1a\u7b2c\u4e00\u90e8\u5206\u4ecb\u7ecd\u7528Python \u7f16\u7a0b\u6240\u5fc5\u987b\u4e86\u89e3\u7684\u57fa\u672c\u6982\u5ff5\uff0c\u5305\u62ecmatplot... "
                }
            }
        }
    }
}
</script>
