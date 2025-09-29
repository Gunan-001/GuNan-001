<template>
    <ul>
        <!--  商品卡片  -->
        <li v-for="item in listTable" :key="item.starId" @click="myRouter(`/mall/detail?commodityId=${item.id}`)">
            <i @click.stop="delCollectObj(item.starId)" v-if="candel" class="el-icon-close"></i>
            <img :src="baseURL+item.commodityImage">
            <h2>{{ item.commodityName }}</h2>
            <h3>{{ item.commodityInformation }}</h3>
            <span>{{ item.commodityPrice }}元</span>
        </li>
        <!--  用于首页部分展示  -->
        <li class="more" v-if="isHide" @click="myRouter('/mall/allgoods')">
            <span></span>
            <div><a>浏览更多 >></a></div>
            <span></span>
        </li>
    </ul>
</template>

<script>
import request from "@/utils/request";
export default {
    name: "MyList",
    props: {
        resList: {
            type: Array,
            default: () => []
        },
        isHide: {
            type: Boolean,
            default: () => false
        },
        candel: {
            type: Boolean,
            default: () => false
        },
        myRouter: {
            function: ()=>{}
        },
        delCollectObj:{
            function: ()=>{}
        }
    },
    data() {
        return {
            listTable: [],
            baseURL:request.baseUrl
        }
    },
    methods:{

    },
    created() {
        if (this.isHide){
            if (this.resList.length>6){
                this.listTable = this.resList.slice(0,7)
            }else {
                this.listTable = this.resList
            }
        }else {
            this.listTable = this.resList
        }
    }
}
</script>

<style lang="less" scoped>
ul {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    flex-flow: row wrap;

    li {
        cursor: pointer;
        padding: 10px 0;
        position: relative;
        display: block;
        width: 234px;
        height: 280px;
        background-color: white;
        margin: 0 0 14.5px 13.7px;

        .el-icon-close{
            position: absolute;
            right: 5px;
            top: 5px;
            opacity: 0;
        }

        .el-icon-close:hover{
            color: #ff6700;
            cursor: pointer;
        }

        img {
            width: 160px;
            height: 160px;
        }

        h2 {
            font-family: "Microsoft YaHei", serif;
            font-size: 14px;
            margin: 25px 10px 0;
        }

        h3 {
            font-family: "Microsoft YaHei", serif;
            font-size: 12px;
            color: #B0B0B0;
        }

        span {
            display: block;
            font-size: 16px;
            color: #ff6700;
            margin: 10px;
        }
    }

    li:hover {
        z-index: 2;
        -webkit-box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
        box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
        -webkit-transform: translate3d(0, -2px, 0);
        transform: translate3d(0, -2px, 0);
    }

    li:hover >.el-icon-close{
        opacity: 1;
    }

    .more {
        display: flex;
        flex-flow: column nowrap;
        justify-content: space-between;
        font-size: 18px;
    }
}
</style>
