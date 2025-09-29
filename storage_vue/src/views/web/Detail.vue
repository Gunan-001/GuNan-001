<template>
    <div id="details">
        <!--  /商品标题  -->
        <div class="top-title">
            <div class="title-wapper">
                <p>{{commodityDetail.commodityName}}</p>
                <ul>
                    <li>概述</li>
                    <li>参数</li>
                    <li>用户评价</li>
                </ul>
            </div>
        </div>
        <!--  /商品标题  -->
        <!--  /商品主体展示  -->
        <div class="middle-main" v-loading="loading">
            <div class="left-show-img">
                <img :src="baseURL+commodityDetail.commodityImage">
            </div>
            <div class="right-show-info">
                <h1>{{commodityDetail.commodityName}}</h1>
                <p class="information">{{commodityDetail.commodityInformation}}</p>
                <p class="subtitle">蔬果购</p>
                <p class="subinformation">{{commodityDetail.commodityPrice}}元</p>
                <el-divider></el-divider>
                <!--  /价格展示区  -->
                <div class="btn-wapper">
                    <div class="wappertext">
                        <span>{{commodityDetail.commodityName}}</span>
                        <span>{{commodityDetail.commodityPrice}}元</span>
                    </div>
                    <p>总计：{{commodityDetail.commodityPrice}}元</p>
                </div>
                <!--  /价格展示区  -->
                <!--  /按钮  -->
                <div class="btn-group">
                    <el-button type="danger" @click="addToShopCar(commodityDetail.id)">加入购物车</el-button>
                    <el-button v-if="starList.indexOf(commodityDetail.id)>=0" disabled type="info">
                        <i class="el-icon-star-on" style="font-size: 16px"></i>已收藏</el-button>
                    <el-button v-else type="info" @click="addToStar(commodityDetail.id)">喜欢</el-button>
                </div>
                <!--  按钮/  -->
                <!--  /底部描述  -->
                <div class="pro-policy">
                    <ul style="color: #B0B0B0;">
                        <li>
                            <i class="el-icon-circle-check"></i> 7天无理由退货
                        </li>
                        <li>
                            <i class="el-icon-circle-check"></i> 7天价格保护
                        </li>
                    </ul>
                </div>
                <!--  底部描述/  -->
            </div>
        </div>
        <!--  商品主体展示/  -->
    </div>
</template>
<script>
import request from "@/utils/request";

export default {
    data() {
        return {
            dis:false,
            commodityDetail:{},
            starList:[],
            baseURL:request.baseUrl,
            loading:false
        };
    },
    methods: {
        /* 回到顶部 */
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
        /* 获取数据 */
        async getData() {
            this.loading = true
            let commodityId = this.$route.query.commodityId || 0
            let res = await request.getCommodityDataForId(commodityId)
            if (res) {
                this.commodityDetail = res.data.commoInfo[0]
                this.loading = false
            }
        },
        /* 获取收藏 */
        async getStarData() {
            let uid = sessionStorage.getItem('userId')
            let res = await request.getCollectData(uid)
            if (res) {
                this.starList = res.data.starList.map(item=>{return item.id})
            }
        },
        /* 添加到收藏 */
        async addToStar(cid) {
            let uid = sessionStorage.getItem('userId')
            if (uid === null) {
                this.$message.error('请登录后执行此操作')
                return
            }
            let params = {
                userId: uid,
                commodityId: cid
            }
            let res = await request.addCommodityToStar(params)
            if (res) {
                this.$message.success('收藏成功')
                await this.getData()
                await this.getStarData()
            }
        },
        /* 添加到购物车 */
        async addToShopCar(cid){
            let uid = sessionStorage.getItem('userId')
            if (uid===null){
                this.$message.error('请登录后执行此操作')
                return
            }
            let params = {
                userId: uid,
                commodityId: cid,
                commodityNumber:1
            }
            let res = await request.addcommodityToShopCar(params)
            if (res){
                await this.$store.dispatch('getShopNum')
                this.$message.success('加入成功')
            }
        }
    },
    mounted() {
        this.getData()
        this.getStarData()
        this.backTop()
    }
};
</script>
<style lang="less" scoped>
/* 标题左侧描述 */
ul{
    margin: 0;
    padding: 0;
    list-style: none;
    display: flex;
    flex-flow: row nowrap;
    li{
        line-height: 65px;
        padding-right: 20px;
    }
}
/* 标题栏 */
.top-title{
    height: 65px;
    box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.07);
    .title-wapper {
        width: 1225px;
        margin: 0 auto;
        display: flex;
        flex-flow: row nowrap;
        justify-content: space-between;
        p {
            margin: 0;
            line-height: 65px;
        }
    }
}
/* 主体栏 */
.middle-main{
    width: 1225px;
    display: flex;
    flex-flow: row nowrap;
    margin: 0 auto 20px;
}
/* 左侧图片 */
.left-show-img{
    width: 560px;
    height: 560px;
    img{
        width: 100%;
    }
}
/* 右侧描述 */
.right-show-info{
    margin-left: 25px;
    width: 640px;
    padding-top: 30px;
    h1{
        text-align: left;
        font-size: 24px;
    }
    p{
        text-align: left;
    }
    .information{
        color: #B0B0B0;
    }
    .subtitle, .subinformation{
        color: #ff6700;
    }
    .btn-wapper{
        padding: 30px 60px;
        margin: 50px 0;
        position: relative;
        background-color: #f9f9fa;
        span{
            font-size: 16px;
        }
        p{
            height: 51px;
            line-height: 51px;
            margin-bottom: 0;
            font-size: 24px;
            color: #ff6700;
        }
        .wappertext{
            display: flex;
            flex-flow: row nowrap;
            justify-content: space-between;
        }
    }
    .btn-group{
        display: flex;
        flex-flow: row nowrap;
        justify-content: space-between;
        .el-button:first-child{
            width: 340px;
            height: 55px;
            background-color: #ff6700;
            border-radius: 0;
        }
        .el-button:last-child{
            width: 260px;
            height: 55px;
            border-radius: 0;
        }
    }
}
</style>
