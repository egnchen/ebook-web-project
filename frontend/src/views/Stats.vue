<template>
<v-container fluid grid-list-md>
<v-layout row wrap justify-space-around>
    <v-flex sm12>
        <v-card color="grey lighten-4">
            <v-card-title>
                <v-layout column align-start>
                    <div class="display-2 text-uppercase">购书统计</div>
                    <div class="display-1 text-uppercase">{{ role }} {{ purchasePrompt }}</div>
                </v-layout>
            </v-card-title>
            <v-card-text>
                <v-chart :options="bookStat" />
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <span v-if="!!startDate && !!endDate">
                    从{{ startDate }}到{{ endDate }}
                </span>
                <v-dialog v-model="datePickerDialog" width="500">
                    <template v-slot:activator="{on}">
                         <v-btn color="primary" v-on="on">过滤</v-btn>
                    </template>
                    <v-card>
                        <v-card-title>修改日期与用户</v-card-title>
                        <v-card-text>
                            <v-layout row wrap>
                                <v-flex xs12 lg6>
                                    <v-menu ref="menu1" v-model="menu1"
                                        :close-on-content-click="false" :nudge-right="40"
                                        lazy transition="scale-transition"
                                        offset-y full-width max-width="290px" min-width="290px" >
                                        <template v-slot:activator="{ on }">
                                            <v-text-field
                                                v-model="startDate"
                                                label="开始日期"
                                                persistent-hint
                                                readonly
                                                prepend-icon="event"
                                                @blur="startDate = parseDate(dateFormatted)"
                                                v-on="on" ></v-text-field>
                                        </template>
                                            <v-date-picker v-model="startDate" no-title @input="menu1 = false"></v-date-picker>
                                    </v-menu>
                                </v-flex>
                            
                                <v-flex xs12 lg6>
                                    <v-menu
                                        v-model="menu2"
                                        :close-on-content-click="false"
                                        :nudge-right="40"
                                        lazy
                                        transition="scale-transition"
                                        offset-y
                                        full-width
                                        max-width="290px"
                                        min-width="290px"
                                    >
                                        <template v-slot:activator="{ on }">
                                        <v-text-field
                                            v-model="endDate"
                                            label="结束日期"
                                            persistent-hint
                                            prepend-icon="event"
                                            readonly
                                            v-on="on"
                                        ></v-text-field>
                                        </template>
                                        <v-date-picker v-model="endDate" no-title @input="menu2 = false"></v-date-picker>
                                    </v-menu>
                                </v-flex>
                                <v-flex xm12 lg6>
                                    <v-text-field v-model="userName" label="用户名" prepend-icon="user"/>
                                </v-flex>
                            </v-layout>
                        </v-card-text>
                    </v-card>
                </v-dialog>
            </v-card-actions>
        </v-card>
    </v-flex>
</v-layout>
</v-container>
</template>

<style scoped>
.echarts {
    width: 100%;
    height: 100%;
    min-height: 300px;
}
</style>


<script>
    import StatDetailCard from '../components/StatDetailCard'
    import ECharts from 'vue-echarts'

    export default {
    components: { StatDetailCard, 'v-chart': ECharts },
    data() {
        return {
            gradientColors: ['red', 'orange', 'yellow'],
            gradientDirection: 'top',
            smooth: 10,
            totalPurchase: 0,
            lineWidth: 1.5,
            userName: "",
            path: [
                {
                    text: '统计',
                    disabled: false,
                    href: 'admin/stats'
                }
            ],
            bookStat: {
                legend: {},
                tooltip: {},
                dataset: {
                    source: [
                        ['book', 'amount']
                    ]
                },
                series: [
                    {type: 'pie'}
                ]
            },
            startDate: '', endDate: '',
            datePickerDialog: false
        }
    },
    watch: {
        datePickerDialog(newVal) {
            if(newVal === false) {
                // closed
                let getParams = {startTime: this.startDate, endTime: this.endDate}
                if (this.userName)
                    getParams['username'] = this.userName
                this.$axios.get("/stats/books", {params: getParams})
                .then(response => {
                    let ret = response.data
                    let arr = []
                    for(var prop in ret) {
                        if (prop === '_totalPurchase')
                            this.totalPurchase = ret[prop]
                        else
                            arr.push([prop, ret[prop]])
                    }
                    this.bookStat.dataset.source = 
                        [['book', 'amount']].concat(arr)
                    console.log(this.bookStat.dataset.source)
                })
                .catch(error => {
                    this.$store.commit("setPrompt", "Request failed, check your params!")
                })
            }
        }
    },
        computed: {
            purchasePrompt() {
                if (this.totalPurchase > 0)
                    return `消费总额： CNY${this.totalPurchase}`
                else
                    return "无购买数据";
            },
            role() {
                if (this.userName)
                    return this.userName
                else
                    return "所有用户"
            }
        },
    created() {
        this.$axios.get("/stats/books")
        .then(response => {
            let ret = response.data
            let arr = []
            for(var prop in ret) {
                if (prop === '_totalPurchase')
                    this.totalPurchase = ret[prop]
                else
                    arr.push([prop, ret[prop]])
            }
            this.bookStat.dataset.source = 
                [['book', 'amount']].concat(arr)
        })
        .catch(error => {
            this.$store.commit("setPrompt", "请求失败")
        })
    },
    methods: {
        parseDate (date) {
            if (!date)
                return null
            const [month, day, year] = date.split('/')
            return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
        }
    }
}
</script>

