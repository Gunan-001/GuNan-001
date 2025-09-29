<template>
    <div class="par-main">
        <van-dialog v-model="show" title="模拟支付" show-cancel-button @confirm="confirmPay" @cancel="cancelPay">
            <div class="pay-window">
                <span style="font-size: 18px">付款到蔬果购平台</span>
                <div style="height: 50px"></div>
                <span style="font-size: 30px">￥{{order.commodityPrice}}元</span>
            </div>
        </van-dialog>
        <div v-if="!show && ok" class="okpay">
            <el-col :sm="12" :lg="6">
                <el-result icon="success" title="支付成功" subTitle="请关闭此界面">
                </el-result>
            </el-col>
        </div>
        <div v-if="!show && !ok" class="nopay">
            <el-col :sm="12" :lg="6">
                <el-result icon="error" title="支付失败" subTitle="请关闭此界面">
                </el-result>
            </el-col>
        </div>
    </div>
</template>

<script>

import request from "@/utils/request";

export default {
    name: "Pay",
    data(){
        return{
            order:{},
            show:true,
            ok:true
        }
    },
    methods:{
        async getData() {
            const id = this.$route.query.id
            let res = await request.getOrderDataForOrderId(id)
            console.log(res)
            if (res){
                if (res.data.orderList.length>0){
                    this.order = res.data.orderList[0]
                }else {
                    this.cancelPay()
                }
            }
        },
        async confirmPay() {
            let params = [{
                id: this.order.id,
                orderStatus: 1
            }]
            let res = await request.payMentOrder(params)
            if (res){
                this.show = false
                this.ok = true
            }else {
                this.cancelPay()
            }
        },
        cancelPay(){
            this.show = false
            this.ok=false
        }
    },
    mounted() {
        this.getData()
    }
}
</script>

<style lang="less" scoped>
html,body{
    height: 100%;
}
.par-main{
    height: 100%;
    background-color: #F7F8FA;
}
.pay-window{
    height: 200px;
    box-sizing: border-box;
    padding: 45px 0 25px;
}
.okpay{
    margin-top: 90px;
}
.nopay{
    margin-top: 90px;
}
</style>
