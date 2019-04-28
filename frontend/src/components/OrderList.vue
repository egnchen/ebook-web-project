<template>
<v-card>
    <v-card-title>
        所有订单
        <v-spacer></v-spacer>
        <v-text-field
            v-model="searchString" append-icon="fas fa-search" label="搜索"
            single-line hide-details />
    </v-card-title>
    <v-data-table :headers="headers" :items="order" class="elevation-1" :search="searchString">
        <template v-slot:items="props">
            <tr @click="props.expanded = !props.expanded">
                <td>{{ props.item.updateTime }}</td>
                <td>{{ props.item.status }}</td>
            </tr>
        </template>
        <template v-slot:expand="props">
          <v-card flat>
              <v-layout>
                  <v-flex xs12 v-for="(itm,idx) in props.item.orderItems" :key="idx">
                      书名：{{ itm.book.title }} 数量：{{ itm.amount }}
                  </v-flex>
              </v-layout>
          </v-card>
        </template>
        <v-alert v-slot:no-results :value="true" color="error" icon="fas fa-warning">
          Your search for "{{ searchString }}" found no results.
        </v-alert>
    </v-data-table>
</v-card>
</template>

<script>
export default {
    data() {
        return {
            order: [],
            headers: [
                {
                    text: '日期',
                    value: 'updateTime'
                },
                {
                    text: '状态',
                    value: 'status'
                }
            ],
            searchString: ''
        }
    },
    created() {
        let vm = this
        this.$axios.get("/orders")
        .then(response => {
            vm.order = response.data
        })
        .catch(error => {
            vm.$state.commit("setPrompt", `error, ${error}`)
        })
    }
}
</script>
