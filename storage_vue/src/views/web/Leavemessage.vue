<template>
    <div id="message">
        <div class="top-title">
            <!-- /标题栏 -->
            <div class="title-wapper">
                <span><i class="el-icon-chat-line-square"></i> 留言板</span>
            </div>
            <!-- 标题栏/ -->
        </div>
        <!-- /主体 -->
        <div class="middle-main" v-loading="loading">
            <div class="messages-wapper">
                <div v-if="messageList.length!==0" class="message-bar">
                    <!-- /留言列表 -->
                    <ul>
                        <li v-for="(item,index) in messageList" :key="item.id">
                            <div class="ones-message">
                                <!-- 头像 -->
                                <div class="left-acatar">
                                    <img src="@/assets/user.png">
                                </div>
                                <!-- 信息 -->
                                <div class="right-info">
                                    <span style="font-size: 12px;color: #6d757a">{{item.userName}}</span>
                                    <span style="margin-left: 10px;background-color: #ff6700;font-size: 10px;padding: 0 3px;color: #FFFFFF;border-radius: 3px">lv1</span>
                                    <span style="margin-left: 10px;font-size: 14px">第{{index+1}}楼</span>
                                    <p style="font-size: 14px">{{item.messageInfo}}</p>
                                    <span style="font-size: 14px;color: #6d757a">{{item.messageTime}}</span>
                                    <div style="height: 5px"></div>
                                    <!-- 商家回复 -->
                                    <div v-if="item.shopBack&&item.shopBack!==''" class="shopBack-info">
                                        <div class="back-acatar">
                                            <img src="@/assets/logo.png">
                                        </div>
                                        <div class="back-info">
                                            <span style="font-size: 12px;color: #6d757a">蔬果购</span>
                                            <span style="margin-left: 10px;background-color: #ff6700;font-size: 10px;padding: 0 3px;color: #FFFFFF;border-radius: 3px">lv10</span>
                                            <span style="margin-left: 10px;font-size: 14px;color: #ff6700">商家回复</span>
                                            <p style="font-size: 14px">{{item.shopBack}}</p>
                                            <span style="font-size: 14px;color: #6d757a">{{item.backTime}}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <!-- 留言列表/ -->
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
                <!-- 列表为空 -->
                <div v-else class="message-empty">
                    <img src="@/assets/cart-empty.png">
                    <div>
                        <h2>暂无留言记录!</h2>
                        <span>快来抢沙发吧!</span>
                    </div>
                </div>
  <!-- /留言输入栏 -->
<div class="message-footer">
    <el-input
        type="textarea"
        :rows="2"
        :autosize="{ minRows: 2, maxRows: 4}"
        placeholder="快来留言吧"
        v-model="messageInfo"
        @input="onMessageInput">
    </el-input>
    <div class="word-count" :class="{ 'text-danger': currentLength > 200 }">
        {{ currentLength }} / 200 字
    </div>
    <el-button type="danger" @click="sendMessage">留言</el-button>
</div>
<!-- /留言输入栏 -->
            </div>
        </div>
        <!-- /主体 -->
    </div>
</template>

<script>

import request from "@/utils/request";

export default {
    name:'Order',
    data(){
        return{
            messageList:[],
            messageInfo:'',
            pageSize:10,
            total:2,
            currentPage:1,
            loading:false,
            currentLength: 0 // 添加字数统计变量
        }
    },
    methods:{
        /* 实时更新字数 */
        onMessageInput() {
            this.currentLength = this.messageInfo.length;
        },
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
        /* 分页点击 */
        async currentChange(currentPage) {
            this.currentPage = currentPage;
            await this.getData()
        },
        /* 获取数据 */
        async getData() {
            this.loading = true
            let params = {
                pageSize:this.pageSize,
                currentPage:this.currentPage
            }
            let res = await request.getMessageData(params)
            if (res){
                let messageList = res.data.commodityList
                messageList.forEach(item=>{
                    item.messageTime = item.messageTime?.replace('T',' ')?.split('.')[0]
                    item.backTime = item.backTime?.replace('T',' ')?.split('.')[0]
                })
                this.total = res.data.totalPage
                this.messageList = messageList
                this.loading = false
            }
        },
        /* 发送留言 */
        // async sendMessage() {
        //     if (this.messageInfo.trim() === '') {
        //         this.$message.error('留言内容不能为空')
        //         return
        //     }
        //     let userId = sessionStorage.getItem('userId')
        //     console.log(userId);
        //     let params = {messageInfo: this.messageInfo,userId:userId}
        //     let res = await request.sendMessage(params)
        //     if (res){
        //         this.$message.success('发送成功')
        //         this.messageInfo = ''
        //         await this.getData()
        //     }
        // }


        async sendMessage() {
            if (this.messageInfo.trim() === '') {
                this.$message.error('留言内容不能为空')
                return
            }
            
            if (this.messageInfo.length > 200) {
                this.$message.error('留言内容不能超过200字')
                return
            }
            
            // 增加登录检查
            let userId = sessionStorage.getItem('userId');
            if (!userId) {
                this.$message.error('请先登录');
                this.$router.push('/login'); // 跳转到登录页
                return;
            }
            
            let params = {messageInfo: this.messageInfo, userId: userId}
            let res = await request.sendMessage(params)
            if (res){
                this.$message.success('发送成功')
                this.messageInfo = ''
                this.currentLength = 0 // 重置字数统计
                await this.getData()
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
#message{
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
    .messages-wapper{
        width: 1225px;
        margin: 0 auto;
        position: relative;
        .message-bar{
            margin-bottom: 20px;
            text-align: right;
            ul{
                list-style: none;
                display: flex;
                flex-flow: column;
                justify-content: flex-start;
                margin-bottom: 20px;
                li >div{
                    width: 1225px;
                    display: flex;
                    flex-flow: row nowrap;
                    justify-content: space-between;
                    box-sizing: border-box;
                    padding: 20px 0 20px;
                    border-bottom: 1px #e5e9ef solid;
                    font-size: 18px;
                    .left-acatar{
                        width: 96px;
                        height: 100%;
                        text-align: center;
                        img{
                            width: 48px;
                            height: 48px;
                            border-radius: 50%;
                            overflow: hidden;
                        }
                    }
                    .right-info{
                        width: 1129px;
                        height: 100%;
                        text-align: left;
                        .shopBack-info{
                            display: flex;
                            flex-flow: row nowrap;
                            justify-content: flex-start;
                            .back-acatar{
                                width: 26px;
                                img{
                                    width: 24px;
                                    height: 24px;
                                    margin-right: 2px;
                                    border-radius: 50%;
                                    overflow: hidden;
                                }
                            }
                            .back-info{
                                width: 1103px;
                                height: 100%;
                                text-align: left;
                            }
                        }
                    }
                }
            }
        }
        .message-empty{
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
.message-footer {
    width: 1225px;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    box-sizing: border-box;
    
    .word-count {
        position: absolute;
        bottom: 5px;
        right: 198px;
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
}
</style>
