<template>
<BookCard :book="book">
    <template v-slot:action="bookProps">
        <div class="action-container">
            <v-dialog v-model="dialogVis" max-width="600px">
                <template v-slot:activator="{ on }">
                    <v-btn color="primary" v-on="on">
                        <v-icon>fas fa-edit</v-icon>
                        编辑
                    </v-btn>
                </template>
                <EditBookDialogCard :book="book" @close-dialog="dialogVis = false"></EditBookDialogCard>
            </v-dialog>
        </div>
    </template>
</BookCard>
</template>

<style scoped>
.action-container {
    height: 100%;
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-items: center;
}
</style>

<script>
import BookCard from './BookCard'
import EditBookDialogCard from './EditBookDialogCard'

export default {
    props: ['book', 'idxData'],
    components: {BookCard, EditBookDialogCard},
    data() {
        return {
            stock: 0,
            dialogVis: false,
            ISBN: 123567890
        }
    },
    methods: {
        editBook(idx){
            new EditBookDialog().$mount(this.$refs.diag)
        }
    }
}
</script>