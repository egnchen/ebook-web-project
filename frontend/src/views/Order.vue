<template>
<v-layout justify-center>
    <v-flex sm12 lg9>
        <h2>订单管理</h2>
        <Order></Order>
    </v-flex>
</v-layout>
</template>

<script>
import Order from '../components/OrderList'

export default {
    components: {Order},
    
}
</script>

