<template>
    <div id="order">
        <!--  /头部标题栏  -->
        <div class="top-title">
            <div class="title-wapper">
                <span><i class="el-icon-s-order"></i> 我的订单</span>
            </div>
        </div>
        <!--  头部标题栏/  -->
        <!--  /主体  -->
        <div class="middle-main" v-loading="loading">
            <!--  /订单展示容器  -->
            <div class="orders-wapper">
                <div v-if="orderDetail.length!==0" class="order-bar">
                    <!--  /订单展示列表  -->
                    <div v-for="item in orderDetail" :key="item.id" class="order">
                        <!--  /订单标签头  -->
                        <div class="order-header">
                            <span>订单编号：{{item.orderNumber}}</span>
                            <span>
                                <span style="color: black">订单时间：{{item.orderTime}}</span>
                                <span v-if="item.orderStatus===0" style="margin-left: 20px;color: #ff6700;cursor: pointer" @click="payOrder(item.id)">
                                    待支付
                                </span>
                                <span v-else-if="item.orderStatus===1" style="margin-left: 20px;color: #03ffc4;cursor: pointer" @click="$message.info('商家正在发货，请耐心等待')">
                                    待发货
                                </span>
                                <span v-else style="margin-left: 20px;color: #09ff00;cursor: pointer" @click="$router.push('/mall/leavemessage')">
                                    待评价
                                </span>
                            </span>
                        </div>
                        <!--  订单标签头/  -->
                        <!--  /订单表  -->
                        <div class="order-table">
                            <el-table
                                :data="item.orderDetail"
                                style="width: 100%"
                                :cell-style="{'font-size':'16px','color':'#424242'}">
                                <el-table-column
                                    width="80">
                                </el-table-column>
                                <el-table-column
                                    prop="image"
                                    width="125">
                                    <template slot-scope="scope" >
                                        <img @click="handelColumnClick(scope.row)" class="order-img"  :src="baseURL+scope.row.image">
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    prop="name"
                                    label="商品名称"
                                    width="380">
                                    <template slot-scope="scope" >
                                        <span class="ones-column" @click="handelColumnClick(scope.row)">{{scope.row.name}}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    prop="price"
                                    label="单价">
                                    <template slot-scope="scope" >
                                        <span>{{scope.row.price}}元</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    prop="number"
                                    label="数量">
                                </el-table-column>
                                <el-table-column
                                    label="小计">
                                    <template slot-scope="scope" >
                                        <span style="color: #ff6700">{{scope.row.price*scope.row.number}}元</span>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                        <!--  订单表/  -->
                        <!--  /订单底部  -->
                        <div class="order-footer">
                            <span>共<span style="color: #ff6700"> {{item.orderDetail.length}} </span>件商品</span>
                            <span style="color: #ff6700">合计：<span style="font-size: 30px">{{item.commodityPrice}}元</span></span>
                        </div>
                        <!--  /订单底部  -->
                    </div>
                    <!--  /订单展示列表  -->
                </div>
                <!-- 列表为空 -->
                <div v-else class="order-empty">
                    <img src="@/assets/cart-empty.png">
                    <div>
                        <h2>您的订单列表还是空的!</h2>
                        <span>快去购物吧!</span>
                    </div>
                </div>
                <div></div>
            </div>
            <!--  /订单展示容器  -->
        </div>
        <!--  主体/  -->
        <!--  /支付弹窗（移除二维码后保留弹窗核心功能）  -->
        <el-dialog
            title="请使用支付宝完成支付"
            :visible.sync="payDialogVisible"  
            width="250px"
            align="center">
            <div style="width: 100%;height: 80px;line-height: 30px;text-align: center;">
                请在新打开的页面完成支付，支付后点击下方按钮
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancelPay" type="info">取消支付</el-button>
                <el-button type="primary" @click="checkPayResult">完成支付</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import request from "@/utils/request";
// 1. 移除QRCode引入（原二维码生成依赖）

export default {
    name:'Order',
    data(){
        return{
            orderDetail:[],
            loading:false,
            baseURL:request.baseUrl,
            payDialogVisible:false,  // 2. 重命名变量：原qrcode→payDialogVisible，语义更清晰
            orderId:null
        }
    },
    methods:{
        // 回到顶部
        backTop() {
            const timer = setInterval(function() {
                const top = document.documentElement.scrollTop || document.body.scrollTop;
                const speed = Math.floor(-top / 5);
                document.documentElement.scrollTop = document.body.scrollTop =
                    top + speed;

                if (top === 0) {
                    clearInterval(timer);
                }
            }, 20);
        },
        // 商品详情跳转
        handelColumnClick(row){
            this.$router.push(`/mall/detail?commodityid=${row.id}`)
        },
        /* 3. 支付触发逻辑（移除二维码生成代码，保留核心window.open） */
        payOrder(id){
            this.orderId = id;          // 记录当前支付订单ID
            this.payDialogVisible = true; // 打开支付弹窗
            // 保留核心功能：打开支付页面
            window.open("http://localhost:8000/orderDetail/pay?id="+id);
            console.log("触发支付，订单ID：", id);
        },
        /* 4. 取消支付（拆分原goToBuy逻辑，语义更清晰） */
        cancelPay() {
            this.payDialogVisible = false; // 关闭弹窗
            this.orderId = null;           // 清空订单ID，避免误查
        },
        /* 5. 查验支付结果（拆分原goToBuy逻辑，功能单一化） */
        async checkPayResult() {
            this.payDialogVisible = false; // 先关闭弹窗
            
            // 仅当有订单ID时才查验支付结果
            if (this.orderId) {
                try {
                    let orderRes = await request.getOrderDataForOrderId(this.orderId);
                    // 检查订单状态：1为已支付（与原逻辑保持一致）
                    if (orderRes.data.orderList[0].orderStatus === 1) {
                        this.$message.success('支付成功！');
                        this.getData(); // 支付成功后重新获取订单列表，刷新状态
                    } else {
                        this.$message.error('支付失败或尚未完成，请重试');
                    }
                } catch (error) {
                    this.$message.error('查询支付结果失败，请稍后再试');
                    console.error('支付结果查询错误：', error);
                } finally {
                    this.orderId = null; // 无论结果如何，清空订单ID
                }
            } else {
                this.$message.error('支付订单信息异常，请重新发起支付');
            }
        },
        // 获取订单数据
        async getData() {
            this.loading = true;
            try {
                let userId = sessionStorage.getItem('userId');
                if (!userId) {
                    this.$message.warning('请先登录');
                    return;
                }
                let res = await request.getOrderData(userId);
                if (res && res.data && res.data.orderList) {
                    this.orderDetail = res.data.orderList.map(item => {
                        // 解析订单详情JSON字符串
                        item.orderDetail = JSON.parse(item.orderDetail || '[]');
                        // 格式化订单时间（去除T和毫秒）
                        item.orderTime = item.orderTime.replace('T',' ').split('.')[0];
                        return item;
                    });
                }
            } catch (error) {
                this.$message.error('获取订单列表失败，请稍后再试');
                console.error('订单数据获取错误：', error);
            } finally {
                this.loading = false;
            }
        },
    },
    mounted() {
        this.getData();   // 页面加载时获取订单列表
        this.backTop();   // 页面加载时回到顶部（可根据需求决定是否保留）
    }
}
</script>

<style lang="less" scoped>
#order{
    margin: 20px 0;
}
/* 标题样式 */
.top-title{
    border-bottom: 2px #ff6700 solid;
}
.title-wapper{
    width: 1225px;
    margin: 0 auto;
    text-align: left;
    height: 65px;
    line-height: 65px;
    i{
        color: #ff6700;
    }
    span{
        font-size: 28px;
    }
}
/* 主体样式 */
.middle-main{
    width: 100%;
    background-color: #F5F5F5;
    padding: 20px 0;
    // 订单容器
    .orders-wapper{
        width: 1225px;
        margin: 0 auto;
        position: relative;
        .order-bar{
            width: 100%;
        }
        // 空订单页面
        .order-empty{
            box-sizing: border-box;
            padding-left: 100px;
            padding-bottom: 130px;
            margin-top: 65px;
            width: 1225px;
            height: 430px;
            display: flex;
            flex-flow: row nowrap;
            justify-content: flex-start;
            div{
                width: 430px;
                text-align: left;
                padding: 80px;
                h2{
                    font-size: 36px;
                    color: #B0B0B0;
                }
                span{
                    font-size: 20px;
                    color: #B0B0B0;
                }
            }
        }
    }
    // 单个订单样式
    .order{
        width: 1225px;
        margin: 0 auto 50px;
        background-color: #FFFFFF;
        .order-header{
            width: 100%;
            height: 60px;
            padding: 0 26px;
            box-sizing: border-box;
            line-height: 60px;
            border-bottom: 1px #ff6700 solid;
            display: flex;
            flex-flow: row nowrap;
            justify-content: space-between;
            span:first-child{
                color: #ff6700;
            }
        }
        .order-table{
            .el-table{
                /deep/ th{
                    height: 87px;
                    div{
                        font-size: 16px;
                        color: #424242;
                    }
                }
            }
            .order-img{
                width: 80px;
                height: 80px;
                cursor: pointer;
            }
            .ones-column{
                cursor: pointer;
            }
            .ones-column:hover{
                color: #ff6700;
            }
        }
        .order-footer{
            width: 100%;
            height: 60px;
            padding: 0 26px;
            box-sizing: border-box;
            line-height: 60px;
            border-top: 1px #ff6700 solid;
            display: flex;
            flex-flow: row nowrap;
            justify-content: space-between;
        }
    }
    // 最后一个订单去除底部margin
    .order:last-child{
        margin-bottom: 0;
    }

    
    .message-footer {
    width: 1225px;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    box-sizing: border-box;
    
    .word-count {
        position: absolute;
        bottom: 5px;
        right: 210px;
        font-size: 12px;
        color: #606266;
    }
    
    .text-danger {
        color: #f56c6c;
    }
    
    .el-button {
        width: 200px;
        height: 54px;
        background-color: #ff6700;
        border-radius: 0;
        margin-left: 100px;
    }
}
}
</style>