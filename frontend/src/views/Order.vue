<template>
<v-layout justify-center>
    <v-flex sm12 lg9>
        <h2>订单管理</h2>
        <OrderList/>
    </v-flex>
</v-layout>
</template>

<script>
    import OrderList from '../components/OrderList'

    export default {
    components: { OrderList },
    
}
</script>

