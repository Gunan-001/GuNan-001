<template>
    <div id="shopcar">
        <!--  /头部标题栏  -->
        <div class="top-title">
            <div class="title-wapper">
                <span><i class="el-icon-shopping-cart-2"></i> 我的购物车</span>
                <span style="margin-left:20px;font-size: 14px;color: #B0B0B0">温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</span>
            </div>
        </div>
        <!--  头部标题栏/  -->
        <!--  /主体  -->
        <div class="middle-main" v-loading="loading">
            <div class="shopcars-wapper">
                <div v-if="shopCarList.length!==0" class="shopcar-bar">
                    <!--  /商品表  -->
                    <el-table
                        :data="shopCarList"
                        tooltip-effect="dark"
                        style="width: 100%"
                        :cell-style="{'font-size':'16px','color':'#424242'}">
                        <el-table-column width="55px">
                        </el-table-column>
                        <el-table-column
                            prop="image"
                            width="125">
                            <template slot-scope="scope" >
                                <img @click="handelColumnClick(scope.row)" class="shopcar-img"  :src="baseURL+scope.row.commodityImage">
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="commodityName"
                            label="商品名称"
                            width="380">
                            <template slot-scope="scope" >
                                <span class="ones-column" @click="handelColumnClick(scope.row)">{{scope.row.commodityName}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="commodityPrice"
                            label="单价">
                            <template slot-scope="scope" >
                                <span>{{scope.row.commodityPrice}}元</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="commodityNumber"
                            label="数量">
                            <template slot-scope="scope" >
                                <el-input-number size="small" v-model="scope.row.buyNumber" :min="1" @change="addNumber(scope.row)"></el-input-number>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="小计">
                            <template slot-scope="scope" >
                                <span style="color: #ff6700">{{scope.row.commodityPrice*scope.row.buyNumber}}元</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="操作">
                            <template slot-scope="scope" >
                                <el-popconfirm
                                    @confirm="handleDel(scope.row)"
                                    title="确定将该商品移出购物车嘛？"
                                >
                                    <i slot="reference" class="el-icon-error"></i>
                                </el-popconfirm>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!--  商品表/  -->
                    <!--  /底部展示栏  -->
                    <div class="shopcar-footer">
                        <div class="footer-left">
                            <span class="left-text" @click="$router.push('/mall/allgoods')">继续购物</span>
                            <span style="margin: 0 15px;color: #B0B0B0">|</span>
                            <span>共 <span style="color: #ff6700">{{shopCarList.length}}</span> 件商品，已选择 <span style="color: #ff6700">{{shopCarList.length}}</span> 件</span>
                        </div>
                        <div class="footer-right">
                            <span style="color: #ff6700;margin-right: 50px">合计：<span style="font-size: 30px">{{money}}元</span></span>
                            <el-button :class="shopCarList.length!==0?'right-activate-btn':'right-deactivate-btn'" :disabled="shopCarList.length===0" @click="confirmOrder" type="danger">去结算</el-button>
                        </div>
                    </div>
                    <!--  底部展示栏/  -->
                </div>
                <!-- 列表为空 -->
                <div v-else class="shopcar-empty">
                    <img src="@/assets/cart-empty.png">
                    <div>
                        <h2>您的购物车还是空的!</h2>
                        <span>快去购物吧!</span>
                    </div>
                </div>
                <div></div>
            </div>
        </div>
        <!--  主体/  -->
    </div>
</template>

<script>

import request from "@/utils/request";

export default {
    name: 'Order',
    data() {
        return {
            shopCarList: [],
            baseURL:request.baseUrl,
            loading:false
        }
    },
    computed:{
        money(){
            let num = 0
            this.shopCarList.forEach(item=>{
                num+=item.commodityNumber*item.commodityPrice
            })
            return num
        }
    },
    methods: {
        /* 回到首页 */
        backTop() {
            const timer = setInterval(function () {
                const top = document.documentElement.scrollTop || document.body.scrollTop;
                const speed = Math.floor(-top / 5);
                document.documentElement.scrollTop = document.body.scrollTop =
                    top + speed;

                if (top === 0) {
                    clearInterval(timer);
                }
            }, 20);
        },
        /* 商品详情 */
        handelColumnClick(row) {
            this.$router.push(`/mall/detail?commodityId=${row.id}`)
        },
        /* 移除商品 */
        async handleDel(row) {
            let res = await request.delCommodityToShopCar(row.id)
            if (res){
                await this.getData()
            }
        },
        /* 确认订单 */
        confirmOrder(){
            let uid = sessionStorage.getItem('userId')
            this.$router.push('/mall/confirmOrder?uid='+uid)
        },
        /* 添加数量 */
        async addNumber(item) {
            let params = {
                id: item.id,
                userId: sessionStorage.getItem('userId'),
                commodityId: item.commodityId,
                commodityNumber: item.buyNumber
            }
            let res = await request.addcommodityNumberToShopCar(params)
            if (res){
                await this.getData()
            }
        },
        /* 获取数据 */
        async getData() {
            this.loading = true
            let userId = sessionStorage.getItem('userId')
            let res = await request.getShopCarDataForId(userId)
            if (res){
                this.shopCarList = res.data.trolleyList
                await this.$store.dispatch('getShopNum')
                this.shopCarList.forEach(item=>item.buyNumber = item.commodityNumber)
                this.loading = false
            }
        }
    },
    mounted() {
        this.getData()
        this.backTop()
    }
}

</script>

<style lang="less" scoped>
#shopcar {
    margin: 20px 0;
}
/* 头部标题栏 */
.top-title {
    border-bottom: 2px #ff6700 solid;
}
.title-wapper {
    width: 1225px;
    margin: 0 auto;
    text-align: left;
    height: 65px;
    line-height: 65px;

    i {
        color: #ff6700;
    }

    span {
        font-size: 28px;
    }
}
/* 主体 */
.middle-main {
    width: 100%;
    background-color: #F5F5F5;
    padding: 20px 0;

    /* 展示容器 */
    .shopcars-wapper {
        width: 1225px;
        margin: 0 auto;
        position: relative;

        .shopcar-bar {
            width: 100%;
            /* 展示图 */
            .shopcar-img{
                width: 80px;
                height: 80px;
                cursor: pointer;
            }
            /* 展示表 */
            .el-table{
                /deep/ th{
                    height: 87px;
                    div{
                        font-size: 16px;
                        color: #424242;
                    }
                }
            }

            /* 单个商品 */
            .ones-column{
                cursor: pointer;
            }
            .ones-column:hover{
                color: #ff6700;
            }

            /* 底部展示框 */
            .shopcar-footer{
                width: 100%;
                height: 50px;
                line-height: 50px;
                font-size: 16px;
                background-color: #ffffff;
                margin-top: 20px;
                display: flex;
                flex-flow: row nowrap;
                justify-content: space-between;
                .left-text{
                    margin-left: 30px;
                    cursor: pointer;
                }
                .left-text:hover{
                    color: #ff6700;
                }
                .el-button{
                    width: 200px;
                    height: 50px;
                    margin: 0;
                    position: relative;
                    top:-5px;
                    border-radius: 0;
                }
                .right-activate-btn{
                    background-color: #ff6700;
                    border-color: #ff6700;
                }
                .right-deactivate-btn{
                    background-color: #B0B0B0;
                    border-color: #B0B0B0;
                }
            }
        }
        /* 空页面 */
        .shopcar-empty {
            box-sizing: border-box;
            padding-left: 100px;
            padding-bottom: 130px;
            margin-top: 65px;
            width: 1225px;
            height: 430px;
            display: flex;
            flex-flow: row nowrap;
            justify-content: flex-start;

            div {
                width: 430px;
                text-align: left;
                padding: 80px;

                h2 {
                    font-size: 36px;
                    color: #B0B0B0;
                }

                span {
                    font-size: 20px;
                    color: #B0B0B0;
                }
            }
        }
    }
}
</style>
