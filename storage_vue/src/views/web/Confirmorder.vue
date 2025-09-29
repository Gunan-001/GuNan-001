<template>
    <div id="confirmorder">
        <!-- /头部标题栏  -->
        <div class="top-title">
            <div class="title-wapper">
                <span><i class="el-icon-s-order"></i> 确认订单</span>
            </div>
        </div>
        <!--  头部标题栏/  -->
        <!--  /主体  -->
        <div class="middle-main" v-loading="loading">
            <div class="confirmorders-wapper">
                <span style="font-size: 18px;margin-left: 48px">收货地址</span>
                <!--  /地址选择容器  -->
                <div class="address-wapper">
                    <div @click="addressId = item.id" v-for="item in addressList" :key="item.id"
                        :class="item.id === addressId ? 'address selectedaddress' : 'address noselectedaddress'">
                        <i class="el-icon-close" @click.stop="delAddress(item.id)"></i>
                        <p style="font-size: 18px;color: black">{{ item.shoppingName || item.nickName }} <i
                                @click.stop="editForm(item)" class="el-icon-edit-outline"></i>
                        </p>
                        <p style="font-size: 14px;color: #B0B0B0">{{ item.userPhone }}</p>
                        <p style="font-size: 14px;color: #B0B0B0">{{ item.shoppingAddress }}</p>
                    </div>
                    <div @click="openCreateAddress" class="address noselectedaddress create-address">
                        <i class="el-icon-circle-plus-outline" style="font-size: 30px"></i>
                        <span style="font-size: 14px;margin-top: 5px">添加新地址</span>
                    </div>
                </div>
                <!--  地址选择容器/  -->
                <!--  /商品核验容器  -->
                <div class="confirm-main">
                    <span class="main-title">商品及优惠劵</span>
                    <ul class="main-ul">
                        <li>
                            <div v-for="(item, index) in orderForm.orderDetail" :key="index">
                                <img class="confirm-img" :src="baseURL + item.image">
                                <span style="display: inline-block;width: 730px">{{ item.name }}</span>
                                <span style="display: inline-block;width: 110px">{{ item.price }}元 x {{
                                    item.number
                                }}</span>
                                <span style="display: inline-block;width: 200px"></span>
                                <span style="color: #ff6700;display: inline-block;width: 100px">{{
                                    item.price * item.number
                                }}元</span>
                            </div>
                        </li>
                    </ul>
                    <div class="delivery-style">
                        <span style="display: inline-block;font-size: 18px;width: 150px">配送方式</span>
                        <span style="color: #ff6700">包邮</span>
                    </div>
                    <div class="delivery-style">
                        <span style="display: inline-block;font-size: 18px;width: 150px">发票</span>
                        <span style="color: #ff6700;margin-right: 20px">电子发票</span>
                        <span style="color: #ff6700;margin-right: 20px">商品</span>
                        <span style="color: #ff6700">个人明细</span>
                    </div>
                    <div class="overall" v-if="orderForm !== {}">
                        <div>
                            <div class="overall-text">
                                <span>商品件数: </span><span>{{ commodityNumber }}件</span>
                            </div>
                            <div class="overall-text"><span>商品总价: </span><span>{{ orderForm.commodityPrice }}元</span>
                            </div>
                            <div class="overall-text"><span>活动优惠: </span><span>-0元</span></div>
                            <div class="overall-text"><span>优惠劵抵扣: </span><span>-0元</span></div>
                            <div class="overall-text"><span>运费: </span><span>0元</span></div>
                            <div class="overall-text"><span>应付总额: </span><span style="font-size: 30px">{{
                                orderForm.commodityPrice }}元</span></div>
                        </div>
                    </div>
                </div>
                <!--  商品核验容器/  -->
                <!--  /底部按钮组  -->
                <div class="confirm-footer">
                    <div class="btn-group">
                        <el-button type="info" plain @click="$router.push('/mall/shopcar')">返回购物车</el-button>
                        <el-button style="background-color: #ff6700;margin-left: 30px" type="danger"
                            @click="handleSubmit">结算
                        </el-button>
                    </div>
                </div>
                <!--  底部按钮组/  -->
            </div>
        </div>
        <!--  /主体  -->
        <!--  /添加地址弹出框  -->
        <el-dialog :title="formTitle" :visible.sync="dialogVisible" width="29%">
            <el-form ref="Form" :model="addressForm" :rules="formTitle === '新增地址' ? createRules : editRules" label-width="100px">
                <el-form-item prop="shoppingAddress" label="收货地址">
                    <el-input type="textarea" :rows="2" placeholder="请输入完整地址(如:北京市朝阳区建国路88号SOHO现代城2号楼1001)"
                        v-model="addressForm.shoppingAddress">
                    </el-input>
                </el-form-item>
                <el-form-item prop="shoppingName" v-if="formTitle === '新增地址'" label="收货名称">
                    <el-input v-model="addressForm.shoppingName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="shoppingPhone" v-if="formTitle === '新增地址'" label="收货电话">
                    <el-input v-model="addressForm.shoppingPhone" autocomplete="off" @input="formatPhone"></el-input>
                </el-form-item>
                <el-form-item align="left" v-if="formTitle !== '新增地址'" prop="shoppingDefault" label="是否默认">
                    <el-switch v-model="addressForm.shoppingDefault" active-color="#ff6700" :active-value="1"
                        :inactive-value="0">
                    </el-switch>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="clearForm">取 消</el-button>
                <el-button type="primary" @click="handleCreateAddress">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="请使用支付宝完成支付" :visible.sync="payDialogVisible" width="250px" align="center">
            <!-- 修改弹窗提示文本，去掉二维码相关，改为引导打开新页面支付 -->
            <div style="width: 100%;height: 80px;line-height: 30px;text-align: center;">
                请在新打开的页面完成支付，支付后点击下方按钮
            </div>
            <span slot="footer" class="dialog-footer">
                <!-- 按钮事件绑定拆分后的方法 -->
                <el-button @click="cancelPay" type="info">取消支付</el-button>
                <el-button type="primary" @click="checkPayResult">完成支付</el-button>
            </span>
        </el-dialog>

        <!--  二维码生成界面/  -->
    </div>
</template>

<script>

import request from "@/utils/request";

export default {
    name: 'Confirmorder',
    data() {
        return {
            orderForm: {},
            addressList: [],
            addressId: 0,
            dialogVisible: false,
            formTitle: '',
            addressForm: {
                id: null,
                shoppingAddress: null,
                shoppingDefault: null,
                shoppingName: null,
                shoppingPhone: null
            },
            createRules: {
                shoppingAddress: [
                    { required: true, message: '请输入收货地址', trigger: 'blur' },
                    { min: 8, message: '地址需包含省市区+街道/门牌号，至少8个字符', trigger: 'blur' },
                    // 修复：移除[]内不必要的转义符
                    {
                        pattern: /^[\u4e00-\u9fa5a-zA-Z0-9\s-#()，。、]+$/,
                        message: '地址仅支持汉字、数字、字母及常见分隔符（如-、#、()）',
                        trigger: 'blur'
                    }
                ],
                shoppingName: [
                    { required: true, message: '请输入收货人名称', trigger: 'blur' },
                    { min: 2, max: 10, message: '收货人姓名需2-10个字符', trigger: 'blur' }
                ],
                shoppingPhone: [
                    { required: true, message: '请输入收货电话', trigger: 'blur' },
                    {
                        pattern: /^(1\d{10})$|^(0\d{2,3}-\d{7,8})$/, // 修复：去掉-的转义
                        message: '请输入正确的手机号（11位）或固话（如010-12345678）',
                        trigger: 'blur'
                    }
                ],
            },
            editRules: {
                shoppingAddress: [
                    { required: true, message: '请输入收货地址', trigger: 'blur' },
                    { min: 8, message: '地址需包含省市区+街道/门牌号，至少8个字符', trigger: 'blur' },
                    // 修复：移除[]内不必要的转义符
                    {
                        pattern: /^[\u4e00-\u9fa5a-zA-Z0-9\s-#()，。、]+$/,
                        message: '地址仅支持汉字、数字、字母及常见分隔符（如-、#、()）',
                        trigger: 'blur'
                    }
                ],
                shoppingDefault: [
                    { required: true, message: '请选择是否为默认地址', trigger: 'blur' }
                ]
            },
            payDialogVisible: false,
            baseURL: request.baseUrl,
            commodityNumber: 0,
            loading: false,
            orderId: null
        }
    },
    methods: {
        
        /* 手机号格式化 */
        formatPhone() {
            const phone = this.addressForm.shoppingPhone;
            if (phone && phone.startsWith('0') && !phone.includes('-')) {
                // 区号为3位（如010、021）时，第4位加“-”；区号为2位（如02）时，第3位加“-”
                const areaCodeLen = phone.length > 3 ? 3 : 2;
                if (phone.length > areaCodeLen) {
                    this.addressForm.shoppingPhone =
                        phone.slice(0, areaCodeLen) + '-' + phone.slice(areaCodeLen);
                }
            }
        },

        /* 回到顶部 */
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
        /* 新建地址弹出框 */
        openCreateAddress() {
            this.formTitle = '新增地址'
            this.dialogVisible = true
        },
        /* 编辑地址弹出框回显 */
        editForm(row) {
            this.formTitle = '修改地址'
            this.addressForm.id = row.id
            this.addressForm.shoppingAddress = row.shoppingAddress
            this.addressForm.shoppingDefault = row.shoppingDefault
            this.addressForm.shoppingName = row.shoppingName
            this.addressForm.shoppingPhone = row.shoppingPhone
            this.dialogVisible = true
        },
        /* 清空表单 */
        clearForm() {
            this.addressForm.id = null
            this.addressForm.shoppingAddress = null
            this.addressForm.shoppingDefault = null
            this.addressForm.shoppingName = null
            this.addressForm.shoppingPhone = null
            this.$refs.Form.resetFields()
            this.dialogVisible = false
        },
        /* 执行新建 */
        handleCreateAddress() {
            this.$refs.Form.validate(async (valid) => {
                if (valid) {
                    if (this.formTitle === '新增地址') {
                        this.addressForm['userId'] = sessionStorage.getItem('userId')
                        let params = this.addressForm
                        let res = await request.addAddress(params)
                        if (res) {
                            this.$message.success('添加成功')
                            this.clearForm()
                            await this.getAddressData()
                        }
                    } else {
                        this.addressForm['userId'] = sessionStorage.getItem('userId')
                        let params = this.addressForm
                        let res = await request.editAddress(params)
                        if (res) {
                            this.$message.success('修改成功')
                            this.clearForm()
                            await this.getAddressData()
                        }
                    }
                } else {
                    return false;
                }
            });
        },
        /* 删除地址 */
        delAddress(id) {
            this.$confirm('确认删除该地址?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                let res = await request.delAddressData(id)
                if (res) {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    await this.getAddressData()
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        async handleSubmit() {
            this.orderForm.addressId = this.addressId
            // 原 map 逻辑无实际返回值，保留结构不影响，无需修改
            this.orderForm.orderDetail.map(item => {
                return {
                    addressId: this.addressId,
                    commodityPrice: item.commodityPrice,
                    orderDetail: item.orderDetail,
                    orderPay: item.orderPay,
                    userId: item.userId
                }
            })
            let params = this.orderForm
            let res = await request.addOrderData(params)
            if (res) {
                this.orderId = res.data.orderId
                this.$store.state.shopNum = 0
                window.open("http://localhost:8000/orderDetail/pay?id=" + res.data.orderId)
                this.payDialogVisible = true
            }
        },

        async checkPayResult() {
            this.payDialogVisible = false; // 关闭弹窗

            if (this.orderId) {
                let order = await request.getOrderDataForOrderId(this.orderId)
                if (order.data.orderList[0].orderStatus === 1) {
                    this.$message.success('支付成功,2秒后自动跳转')
                    setTimeout(() => {
                        this.$router.push('/mall/allgoods')
                    }, 2000)
                } else {
                    this.$message.error('支付失败,2秒后自动跳转')
                    setTimeout(() => {
                        this.$router.push('/mall/allgoods')
                    }, 2000)
                }
            } else {
                this.$message.error('支付失败,2秒后自动跳转')
                setTimeout(() => {
                    this.$router.push('/mall/allgoods')
                }, 2000)
            }
        },
        /* 新建订单 */
        createOrder(list) {
            this.orderForm = {
                addressId: null,
                userId: this.$route.query.uid,
                orderPay: '支付宝支付',
                commodityPrice: list.reduce((sum, e) => sum + e.commodityNumber * Number(e.commodityPrice || 0), 0),
                orderDetail: list.map(item => {
                    return {
                        id: item.commodityId,
                        name: item.commodityName,
                        number: item.commodityNumber,
                        price: item.commodityPrice,
                        image: item.commodityImage
                    }
                })
            }
            this.commodityNumber = this.orderForm.orderDetail.length
        },
        /* 根据购物车信息生成订单数据 */
        async getData() {
            if (this.$route.query.uid === undefined) {
                await this.$router.push('/mall/shopcar')
                return
            }
            this.loading = true
            let userId = this.$route.query.uid
            let res = await request.getShopCarDataForId(userId)
            if (res && res.data.trolleyList.length > 0) {
                /* 获取数据并判断数据是否为空 为空重定向 */
                this.createOrder(res.data.trolleyList)
                this.loading = false
            } else {
                this.loading = false
                await this.$router.push('/mall/shopcar')
            }
        },
        /* 获取地址信息 */
        async getAddressData() {
            let userId = sessionStorage.getItem('userId')
            let res = await request.getAddressData(userId)
            if (res) {
                this.addressList = res.data.addressList
                if (this.addressList.length > 0) {
                    this.addressId = this.addressList[0].id
                }
            }
        }
    },
    created() {
        this.getData()
        this.getAddressData()
    },
    mounted() {
        this.backTop()
    }
}

</script>

<style lang="less" scoped>
#confirmorder {
    margin: 20px 0;
}

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

.middle-main {
    width: 100%;
    background-color: #F5F5F5;
    padding: 20px 0;
    min-height: 535px;

    .confirmorders-wapper {
        width: 1225px;
        margin: 0 auto;
        position: relative;
        padding-top: 48px;
        text-align: left;
        background-color: #ffffff;

        .address-wapper {
            display: flex;
            flex-flow: row wrap;
            justify-content: flex-start;
            padding: 0 48px;
            margin-top: 20px;

            .address {
                padding: 15px 24px 0;
                margin-right: 17px;
                margin-bottom: 24px;
                box-sizing: border-box;
                width: 270px;
                height: 195px;
                text-align: left;
                cursor: pointer;
                position: relative;

                .el-icon-edit-outline {
                    opacity: 0;
                }

                .el-icon-edit-outline:hover {
                    color: #ff6700;
                }

                .el-icon-close {
                    position: absolute;
                    right: 10px;
                    top: 10px;
                    opacity: 0;
                }

                .el-icon-close:hover {
                    color: #ff6700;
                }
            }

            .address:hover {
                border: 1px #ff6700 solid;
            }

            .address:hover>.el-icon-close,
            .address:hover>p>.el-icon-edit-outline {
                opacity: 1;
            }

            .selectedaddress {
                border: 1px #ff6700 solid;
            }

            .noselectedaddress {
                border: 1px #B0B0B0 solid;
            }

            .create-address {
                display: flex;
                flex-flow: column nowrap;
                justify-content: center;
                text-align: center;

                span {
                    color: #B0B0B0;
                }
            }

            .create-address:hover,
            .create-address:hover>span {
                color: #ff6700;
            }
        }

        .confirm-main {
            box-sizing: border-box;
            padding: 0 48px;

            .main-title {
                line-height: 40px;
            }

            .main-ul {
                list-style: none;
                border-top: 1px #e0e0e0 solid;
                border-bottom: 1px #e0e0e0 solid;
                padding: 5px 0;

                li {
                    display: block;

                    div {
                        height: 30px;
                        line-height: 30px;
                        padding: 10px 0;
                        display: flex;
                        flex-flow: row nowrap;
                        justify-content: flex-start;

                        .confirm-img {
                            width: 30px;
                            height: 30px;
                            margin-right: 20px;
                        }
                    }
                }
            }

            .delivery-style {
                height: 88px;
                box-sizing: border-box;
                padding: 25px 0;
                border-bottom: 1px #e0e0e0 solid;

                span {
                    height: 38px;
                    line-height: 38px;
                    font-size: 14px;
                }
            }

            .overall {
                height: 235px;
                box-sizing: border-box;
                padding: 20px 0;
                text-align: right;
                display: flex;
                flex-flow: row nowrap;
                justify-content: flex-end;

                .overall-text {
                    width: 231px;
                    height: 30px;
                    line-height: 30px;
                    display: flex;
                    flex-flow: row nowrap;
                    justify-content: space-between;
                    font-size: 16px;

                    span:first-child {
                        width: 126px;
                        color: #757575;
                    }

                    span:last-child {
                        color: #ff6700;
                        width: 105px;
                    }
                }

                .overall-text:last-child {
                    height: 40px;

                    span:first-child {
                        margin-top: 10px;
                    }

                    span:last-child {
                        line-height: 40px;
                    }
                }
            }
        }

        .confirm-footer {
            height: 82px;
            box-sizing: border-box;
            padding: 20px 0;
            border-top: 2px #e0e0e0 solid;
            display: flex;
            flex-flow: row nowrap;
            justify-content: flex-end;

            .btn-group {
                margin-right: 40px;

                .el-button {
                    width: 160px;
                    height: 40px;
                    border-radius: 0;
                }
            }
        }
    }
}
</style>
