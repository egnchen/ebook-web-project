<template>
<v-card color="grey lighten-4" padding="10">
    <v-card-title>
        <v-layout column align-start>
            <div class="display-2 text-uppercase">游览人数</div>
            <div class="subheader grey--text text-uppercase">{{ role }}</div>
        </v-layout>
    </v-card-title>
    <v-card-text>
        <v-sheet color="grey lighten-4">
            <v-sparkline :gradient="gradientColors" :gradient-direction="gradientDirection"
                :line-width="width" :smooth="smooth" auto-draw :value="values" padding="20">
                <template v-slot:label="item">
                    {{ item.value }}人
                </template>
            </v-sparkline>
        </v-sheet>
    </v-card-text>
    <v-card-text>
        <v-sheet>
            <v-data-table :headers="headers" :items="statData" class="elevation-1" >
                <template v-slot:items="props">
                    <td>{{ props.item.date }}</td>
                    <td>{{ props.item.value }}</td>
                </template>
            </v-data-table>
        </v-sheet>
    </v-card-text>
</v-card>
</template>

<style scoped>

</style>

<script>
export default {
    props: {
        statData: {
            required: false,
            type: Array,
            default: () => [
                {date: '2019-02-01', value: 20},
                {date: '2019-02-02', value: 30},
                {date: '2019-02-03', value: 45},
                {date: '2019-02-04', value: 75},
                {date: '2019-02-05', value: 55},
                {date: '2019-02-06', value: 10},
                {date: '2019-02-07', value: 56},
                {date: '2019-02-08', value: 34},
            ]
        }
    },
    computed: {
        dates() { return this.statData.map(v => v.date) },
        values() { return this.statData.map(v => v.value) }
    }

}
</script>
