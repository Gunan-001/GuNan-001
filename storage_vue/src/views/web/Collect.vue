<template>
    <div id="collect">
        <!--  /头部标题栏  -->
        <div class="top-title">
            <div class="title-wapper">
                <span><i class="el-icon-collection-tag"></i> 我的收藏</span>
            </div>
        </div>
        <!--  头部标题栏/  -->
        <!--  /主体  -->
        <div class="middle-main" v-loading="loading">
            <div class="collect-wapper">
                <!--  /商品列表  -->
                <div v-if="collectList.length!==0" class="collect-bar">
                    <MyList :resList="collectList" :isHide="false" :candel="true" :myRouter="viewTo" :delCollectObj="handleDel"/>
                </div>
                <!--  商品列表/  -->
                <!-- 列表为空 -->
                <div v-else class="collect-empty">
                    <img src="@/assets/cart-empty.png">
                    <div>
                        <h2>您的收藏列表还是空的!</h2>
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
import MyList from "@/components/MyList";
import request from "@/utils/request";
export default {
    name:'Collect',
    components: { MyList },

    data(){
        return{
            collectList:[],
            loading:false
        }
    },
    methods:{
        viewTo(path) {
            this.$router.push(path)
        },
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
            this.collectList = []
            let userId = sessionStorage.getItem('userId')
            let res = await request.getCollectData(userId)
            if (res){
                this.collectList = res.data.starList
                this.loading = false
            }
        },
        /* 删除收藏 */
        handleDel(id){
            this.$confirm('是否将该商品移出我的收藏?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                let res = await request.delCollectObj(id)
                if (res) {
                    await this.getData()
                    this.$message({
                        type: 'success',
                        message: '执行成功!'
                    });
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    },
    mounted() {
        this.getData()
        this.backTop()
    }
}

</script>

<style lang="less" scoped>
#collect{
    margin: 20px 0;
}
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
.middle-main{
    width: 100%;
    background-color: #F5F5F5;
    padding: 20px 0;
    .collect-wapper{
        width: 1238.69px;
        margin: 0 auto;
        position: relative;
        .collect-bar{
            width: 100%;
        }
        .collect-empty{
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
}
</style>
