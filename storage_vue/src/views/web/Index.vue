<template>
    <div v-loading="loading">
        <!--  /轮播图  -->
        <div class="banner-wapper">
            <el-carousel height="460px">
                <el-carousel-item v-for="item in bannerList" :key="item.id">
                    <img style="background-size: cover;width: 1225px;height: 460px" :src="baseURL+item.path" alt="">
                </el-carousel-item>
            </el-carousel>
        </div>
        <!--  轮播图-END/  -->
        <!--  /主体展示区  -->
        <div style="width: 100%;background-color: #f5f5f5;">
            <div class="hotclass-wapper">
                <!--  /热推列表1  -->
                <div id="hotclass1" v-if="shucaiList.length>0">
                    <div class="hotlist-title">蔬菜</div>
                    <div class="hot-main">
                        <div class="left-img">
                            <img src="@/assets/shucai.png" alt="">
                        </div>
                        <!--  /商品列表  -->
                        <div class="list">
                            <MyList :resList="shucaiList" :isHide="true" :myRouter="viewTo"/>
                        </div>
                        <!--  商品列表/  -->
                    </div>
                </div>
                <!--  热推列表1/  -->
                <!--  /热推列表2  -->
                <div id="hotclass2" v-if="shuiguoList.length>0">
                    <div class="hotlist-title">水果</div>
                    <div class="hot-main">
                        <div class="left-img">
                            <img src="@/assets/shuiguo.png" alt="">
                        </div>
                        <div class="list">
                            <MyList :resList="shuiguoList" :isHide="true" :myRouter="viewTo"/>
                        </div>
                    </div>
                </div>
                <!--  热推列表2/  -->
            </div>
        </div>
        <!--  主体展示区/  -->
    </div>
</template>

<script>
import MyList from "@/components/MyList";
import request from "@/utils/request";

export default {
    name: "Index.vue",
    components:{ MyList },
    data(){
        return{
            bannerList: [],
            shucaiList:[],
            shuiguoList:[],
            baseURL:request.baseUrl,
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
        // 获取轮播图数据
        async getBanner(){
            this.loading=true
            /* 获取轮播图请求 */
            let res = await request.getBannerData()
            if (res){
                this.bannerList = res.data.slideList
                this.loading=false
            }
        },
        async getHotClass() {
            /* 获取热销分类数据 只要7条数据即可 此处写死水果蔬菜两类*/
            let classids = await this.getClassData()
            let shucai = await request.getCommodityDataForCondition({commodityClassId:classids[0],pageSize:15,currentPage:1})
            let shuiguo = await request.getCommodityDataForCondition({commodityClassId:classids[1],pageSize:15,currentPage:1})
            this.shuiguoList = shucai.data.commodityList
            this.shucaiList = shuiguo.data.commodityList
        },
        async getClassData(){
            let res = await request.getCommodityClass()
            return res.data.comodityList.map(item=>{
                return item.id
            })
        }
    },
    mounted() {
        this.getBanner()
        this.getHotClass()
        this.backTop()
    }
}
</script>

<style lang="less" scoped>
/* 轮播图 */
.banner-wapper{
    width: 1225px;
    overflow: hidden;
    margin: 20px auto;
}
/* 热销分类 */
.hotclass-wapper{
    margin: 0 auto;
    width: 1225px;
    padding-bottom: 20px;
    #hotclass1,#hotclass2{
        margin: 20px 0;
        .hotlist-title{
            font-size: 22px;
            width: 44px;
            height: 58px;
            text-align: center;
            line-height: 58px;
        }
        .hot-main{
            width: 100%;
            height: 615px;
            display: flex;
            flex-flow: row nowrap;
            justify-content: flex-start;
            .left-img{
                width: 234px;
                overflow: hidden;
                img{
                    width: 100%;
                }
            }
            .list{
                width: 991px;
            }
        }
    }
}

</style>
