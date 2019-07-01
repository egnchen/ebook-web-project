<template>
<v-card>
    <v-card-title>
        <span class="headline">
            <v-icon>fas fa-edit</v-icon>
            {{ dialogTitle }}
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
                <v-text-field label="ISBN" required v-model="book.isbn" />
            </v-flex>
            
            <v-flex xs12 md6 offset-md1>
                <v-text-field label="库存量" required v-model="book.stock" />
            </v-flex>
            <v-flex xs12 md9>
                <v-textarea label="描述" required v-model="book.description" />
            </v-flex>
            <v-flex xs12 md3>
                <upload-btn title="上传图片" @file-update="fileUpdate"/>
                <img v-if="book.picture.id"
                     :src="imageUrl" contain/>
            </v-flex>
        </v-layout>
    </v-card-text>
    <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn v-if="book.id" color="error" @click="deleteBook">删除</v-btn>
        <v-btn color="primary" @click="save">保存</v-btn>
    </v-card-actions>
</v-card>
</template>

<script>
    import UploadButton from 'vuetify-upload-button'

    export default {
        components: {'upload-btn': UploadButton},
        props: ['book'],
        computed: {
            dialogTitle() {
                if (this.book.id)
                    return "编辑"
                else
                    return "添加"
            },
            imageUrl() {
                if (this.book.picture.path)
                    return this.book.picture.path
                else if (this.book.picture.name)
                    return this.$axios.defaults.baseURL +
                        'picture?id=' + this.book.picture.id
                else
                    return null;
            }
        },
        methods: {
            fileUpdate(file) {
                let vm = this
                let formData = new FormData()
                formData.append("file", file)
                this.$axios.post("/picture", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(response => {
                        vm.book.picture = response.data
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            save() {
                // determine whether modify or add
                if (this.book.id)
                    this.modifyBook();
                else
                    this.addBook();
            },
            modifyBook() {
                let vm = this
                this.$axios.put("/book/" + this.book.id, this.book)
                    .then(response => {
                        vm.$store.commit("setSuccessPrompt", "Book modified.")
                    })
                    .catch(error => {
                        vm.$store.commit("setErrorPrompt", `Failed to modify, error:${error}`)
                    })
                this.$emit("close-dialog")
            },
            addBook() {
                let vm = this;
                this.$axios.post("/book", this.book)
                    .then(response => {
                        vm.$store.commit("setSuccessPrompt", "New book saved.");
                    })
                    .catch(error => {
                        vm.$store.commit(`setErrorPrompt", "Failed to save, error:${error}`)
                    })
            },
            deleteBook() {
                let vm = this;
                this.$axios.delete("/book/" + this.book.id)
                    .then(response => {
                        vm.$store.commit("setSuccessPrompt", `Book deleted, message:${response}`);
                    })
                    .catch(error => {
                        console.log(error)
                        vm.$store.commit("setErrorPrompt", `Failed to delete, error:${error}`)
                    })
            }
        }
}
</script>

