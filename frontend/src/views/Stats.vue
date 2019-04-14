<template>
<v-container fluid grid-list-md>
<v-layout row wrap justify-space-around>
    <v-flex sm12>
        <v-breadcrumbs :items="path" divider='>' />
    </v-flex>
    <v-flex sm12 md6>
        <v-card color="grey lighten-4">
            <v-card-title>
                <v-layout column align-start>
                    <div class="display-2 text-uppercase">消费情况</div>
                    <div class="subheader grey--text text-uppercase">{{ role }}</div>
                </v-layout>
            </v-card-title>
            <v-card-text>
                <v-sheet color="grey lighten-4">
                    <v-sparkline :gradient="gradientColors" :gradient-direction="gradientDirection"
                        :line-width="width" :smooth="smooth" auto-draw :value="value" padding="20">
                        <template v-slot:label="item">
                            ￥{{ item.value }}
                        </template>
                    </v-sparkline>
                </v-sheet>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary">更换角色</v-btn>
                <v-dialog max-width="800px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" v-on="on">详情</v-btn>
                    </template>
                    <StatDetailCard></StatDetailCard>
                </v-dialog>
            </v-card-actions>
        </v-card>
    </v-flex>
    <v-flex sm12 md6>
        <v-card color="grey lighten-4" padding="10">
            <v-card-title>
                <v-layout column align-start>
                    <div class="display-2 text-uppercase">购书统计</div>
                    <div class="subheader grey--text text-uppercase">{{ role }}</div>
                </v-layout>
            </v-card-title>
            <v-card-text>
                <v-sheet color="grey lighten-4">
                    <v-sparkline :gradient="gradientColors" :gradient-direction="gradientDirection"
                        :line-width="width" :smooth="smooth" auto-draw :value="value" padding="20">
                        <template v-slot:label="item">
                            {{ item.value }}本
                        </template>
                    </v-sparkline>
                </v-sheet>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary">更换角色</v-btn>
                <v-dialog max-width="800px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" v-on="on">详情</v-btn>
                    </template>
                    <StatDetailCard></StatDetailCard>
                </v-dialog>
            </v-card-actions>
        </v-card>
    </v-flex>
    <v-flex sm12 md6>
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
                        :line-width="width" :smooth="smooth" auto-draw :value="value" padding="20">
                        <template v-slot:label="item">
                            {{ item.value }}人
                        </template>
                    </v-sparkline>
                </v-sheet>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary">更换角色</v-btn>
                <v-dialog max-width="800px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" v-on="on">详情</v-btn>
                    </template>
                    <StatDetailCard></StatDetailCard>
                </v-dialog>
            </v-card-actions>
        </v-card>
    </v-flex>
</v-layout>
</v-container>
</template>

<script>
import StatDetailCard from '../components/StatDetailCard'

export default {
    components: { StatDetailCard },
    data() {
        return {
            gradientColors: ['red', 'orange', 'yellow'],
            gradientDirection: 'top',
            smooth: 10,
            lineWidth: 1.5,
            role: "总量",
            value: [10,23,52,6,76,21,34,75],
            path: [
                {
                    text: '统计',
                    disabled: false,
                    href: 'admin/stats'
                }
            ]
        }
    }
}
</script>

