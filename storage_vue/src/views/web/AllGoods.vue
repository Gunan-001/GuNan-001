<template>
    <div class="goods">
        <!--  /面包屑  -->
        <div class="breadcrumb">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
                <el-breadcrumb-item>全部商品</el-breadcrumb-item>
                <el-breadcrumb-item v-if="search">搜索</el-breadcrumb-item>
                <el-breadcrumb-item v-else>分类</el-breadcrumb-item>
                <el-breadcrumb-item v-if="search">{{search}}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 面包屑/ -->
        <!-- /分类标签 -->
        <div class="nav">
            <div class="product-nav">
                <div class="title">分类</div>
                <el-tabs v-model="activeName" type="card" @tab-click="handleViewTo(activeName)">
                    <el-tab-pane
                        v-for="item in categoryList"
                        :key="item.id"
                        :label="item.commoditTypeName"
                        :name="''+item.id"
                    />
                </el-tabs>
            </div>
        </div>
        <!-- 分类标签/ -->
        <!-- /主体 -->
        <div class="main" v-loading="loading">
            <!--  /商品列表  -->
            <div class="list">
                <MyList :resList="showList" :isHide="false" :myRouter="viewTo" v-if="showList.length>0"></MyList>
                <div v-else class="none-product">抱歉没有找到相关的商品，请看看其他的商品</div>
            </div>
            <!--  商品列表/  -->
            <!-- /分页 -->
            <div class="pagination">
                <el-pagination
                    background
                    layout="prev, pager, next"
                    :page-size="pageSize"
                    :total="total"
                    @current-change="currentChange"
                ></el-pagination>
            </div>
            <!-- 分页/ -->
        </div>
        <!-- /主体 -->
    </div>
</template>

<script>
import MyList from "@/components/MyList";
import request from "@/utils/request";

export default {
    name: "Goods",
    components:{MyList},
    data(){
        return{
            activeName:'',
            categoryList:[],
            search:'',
            showList:[],
            total: 15, // 商品总量
            pageSize: 15, // 每页显示的商品数量
            currentPage: 1, //当前页码
            loading:false
        }
    },
    methods:{
        // 传入mylist组件用于跳转界面
        viewTo(path) {
            this.$router.push(path)
        },
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
        /* 分页点击跳转 */
        currentChange(currentPage) {
            this.currentPage = currentPage;
            this.getData(this.activeName)
        },
        /* 标签页操作 */
        handleViewTo(id){
            this.$router.push('/mall/allgoods?commodityClassId='+id)
            this.currentPage = 1
            if (this.search){
                this.$router.push('/mall/allgoods?commodityClassId='+id+'&kwd='+this.search)
            }else {
                this.$router.push('/mall/allgoods?commodityClassId='+id)
            }
        },
        /* 获取商品数据 */
        async getData(cid) {
            this.loading = true
            let params = {
                commodityClassId: cid || 0,
                search: this.search || '',
                pageSize: this.pageSize,
                currentPage: this.currentPage
            }
            /* 发送请求 */
            this.showList = []
            let res = await request.getCommodityDataForCondition(params)
            if (res) {
                this.showList = res.data.commodityList
                this.total = res.data.totalPage
                this.loading = false
            }
            this.backTop()
        },
        /* 获取分类数据 */
        async getClass() {
            let res = await request.getCommodityClass()
            if (res){
                this.categoryList = [{id:0,commoditTypeName:'全部'}].concat(res.data.comodityList)
            }
        }
    },
    watch:{
        /* 监视路由变化以便随时更新数据 */
        '$route.query': {
            immediate:true,
            deep:true,         //是否开启深度监视
            handler(to){
                let commodityClassId = to.commodityClassId||0
                this.search = to.kwd||''
                this.getData(commodityClassId)
            }
        }
    },
    mounted() {
        this.backTop()
        this.getData()
        this.getClass()
    }
}
</script>

<style lang="less" scoped>
.goods{
    margin: 20px auto 20px;
    /* 面包屑 */
    .breadcrumb{
        height: 50px;
        .el-breadcrumb{
            width: 1225px;
            margin: 0 auto;
            font-size: 16px;
            font-family: "Microsoft YaHei",serif;
        }
    }
    /* 标签页 */
    .nav{
        border-bottom: 1px #e6e6e6 solid;
        .product-nav{
            width: 1225px;
            margin: 0 auto;
            display: flex;
            flex-flow: row nowrap;
            height: 42px;
            line-height: 42px;
            .title{
                padding: 0 15px 0 0;
                color: black;
                font-weight: bold;
            }
        }
    }
    /* 主体 */
    .main{
        padding: 14px 0;
        background-color: #F5F5F5;
        .list{
            min-height: 535px;
            width: 1238.69px;
            margin: 0 auto;
        }
    }
}
</style>
