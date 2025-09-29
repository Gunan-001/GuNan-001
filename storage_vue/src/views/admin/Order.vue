<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/admin/order">订单模块</a></el-breadcrumb-item>
            <el-breadcrumb-item>订单管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入订单编号">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button type="danger" style="margin-left: 10px" @click="handleAll" plain>批量出库</el-button>
        </div>
        <el-table
            :data="dataList"
            border
            :loading="loading"
            @selection-change="handleSelectionChange"
            :max-height="tableHeight"
            style="width: 100%">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                    {{ scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                prop="orderNumber"
                label="订单编号">
            </el-table-column>
            <el-table-column
                prop="userName"
                label="用户名">
            </el-table-column>
            <el-table-column
                prop="shoppingPhone"
                label="联系电话">
            </el-table-column>
            <el-table-column
                prop="commodityPrice"
                label="订单金额(元)">
            </el-table-column>
            <el-table-column
                prop="orderPay"
                label="支付方式">
                <template slot-scope="scope">
                    <el-link type="primary">{{ scope.row.orderPay }}</el-link>
                </template>
            </el-table-column>
            <el-table-column
                prop="orderStatus"
                label="订单状态">
                <template slot-scope="scope">
                    <el-link v-if="scope.row.orderStatus===0" type="danger">待支付</el-link>
                    <el-link v-if="scope.row.orderStatus===1" type="primary">待发货</el-link>
                    <el-link v-if="scope.row.orderStatus===2" type="success">待评价</el-link>
                </template>
            </el-table-column>
            <el-table-column
                prop="orderTime"
                label="下单时间">
            </el-table-column>
            <el-table-column
                prop="shoppingAddress"
                label="地址"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column label="操作">
            <template slot-scope="scope">
                <el-popover
                    placement="right"
                    trigger="click">
                    <el-table size="mini" border :data="scope.row.orderDetail">
                        <el-table-column label="序号" width="60">
                            <template slot-scope="subscope">
                                {{ subscope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column width="80px" property="name" label="商品"></el-table-column>
                        <el-table-column width="80px" property="number" label="数量"></el-table-column>
                    </el-table>
                    <el-button type="text" size="small" slot="reference">详情</el-button>
                </el-popover>
                <!-- 核心修改：将 disabled 判断条件反转 -->
                <el-popconfirm title="确认执行出库吗？" @confirm="handle(scope.row)">
                    <el-button 
                        :disabled="scope.row.orderStatus!==1"  
                        style="margin-left: 5px" 
                        type="text" 
                        size="small"
                        slot="reference">
                        出库
                    </el-button>
                </el-popconfirm>
            </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "Order",
    data() {
        return {
            tableData: [],
            input: '',
            tableHeight: null,
            multipleSelection: [],
            loading: false,
        }
    },
    methods: {
        async handle(row) {
            let param = [{id:row.id,orderStatus: 2}]
            let res = await request.editOrderInfo(param)
            if (res) {
                this.$message.success('出库成功')
                await this.getData()
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleAll() {
    // 过滤出只有待发货状态的订单
    const validOrders = this.multipleSelection.filter(item => item.orderStatus === 1)
    if (this.multipleSelection.length === 0) {
        this.$message.error('请勾选后批量出库')
        return
    }
    if (validOrders.length === 0) {
        this.$message.error('只能对"待发货"状态的订单执行出库操作')
        return
    }
    this.$alert('出库后无法恢复，确认出库嘛？', '提示', {
        confirmButtonText: '确定',
        callback: async action => {
            if (action === 'confirm') {
                let param = validOrders.map(item => ({id: item.id, orderStatus: 2}))
                let res = await request.editOrderInfo(param)
                if (res) {
                    this.$message.success('出库成功')
                    await this.getData()
                }
            }
        }
    });
},
        async getData() {
            this.loading = true
            let res = await request.getOrderAllData()
            if (res){
                this.tableData = res.data.orderList
                this.tableData.forEach(item=>{
                    item.orderDetail = JSON.parse(item.orderDetail)
                })
                this.loading = false
            }
        }
    },
    computed: {
        dataList() {
            let filList = this.tableData
            return filList.filter(p => {
                return p.orderNumber.indexOf(this.input) !== -1
            })
        }
    },
    mounted() {
        this.tableHeight = document.querySelector('.box-card').clientHeight - 145
        this.getData()
    }
}
</script>

<style lang="less" scoped>
.box-card {
    height: calc((100vh) - (102px));
}

.top-control-group {
    margin-top: 25px;
    margin-bottom: 20px;
    height: 40px;
    display: flex;
    flex-flow: row nowrap;

    .el-input {
        width: 250px;
        height: 40px;
    }
}

</style>
