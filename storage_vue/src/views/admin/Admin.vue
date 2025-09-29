<template>
    <el-container class="home-container">
        <!--头部区域-->
        <el-header>
            <div>
                <img alt="" src="../../assets/logo.png">
                <span>蔬果商城后台管理系统</span>
            </div>
            <el-button type="info" @click="logout">退出</el-button>
        </el-header>

        <el-container>
            <!-- 侧边栏菜单 -->
            <el-aside :width="isCollapsed ? '64px' : '200px'">
                <div class="toggle-button" @click="toggleCollapsed">
                    |||
                </div>
                <el-menu style="text-align: left" background-color="#a4ee80" text-color="#555555" active-text-color="#FFFFFF" :unique-opened="true" :collapse="isCollapsed" :collapse-transition="false" :router="true" :default-active="activePath">
                    <el-submenu index="0" >
                        <template slot="title">
                            <i class="el-icon-s-goods"></i>
                            <span>商品模块</span>
                        </template>
                        <el-menu-item index="/admin/goods">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>商品列表</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="/admin/goodstype">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>商品分类</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu index="1" >
                        <template slot="title">
                            <i class="el-icon-s-order"></i>
                            <span>订单模块</span>
                        </template>
                        <el-menu-item index="/admin/order">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>订单管理</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu index="2" >
                        <template slot="title">
                            <i class="el-icon-s-shop"></i>
                            <span>系统设置</span>
                        </template>
                        <el-menu-item index="/admin/banner">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>轮播图管理</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="/admin/message">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>留言板管理</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu index="3" >
                        <template slot="title">
                            <i class="el-icon-user-solid"></i>
                            <span>用户模块</span>
                        </template>
                        <el-menu-item index="/admin/user">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>用户管理</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                </el-menu>
            </el-aside>
            <!-- 右侧内容主体 -->
            <el-main>
                <!-- 路由占位符 -->
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
export default {
    data () {
        return {
            // 左侧菜单数据
            menuList: [],
            // 是否动态折叠
            isCollapsed: false,
            // 被激活的链接
            activePath: window.sessionStorage.getItem('activePath')||'',
            // 用户身份
            userRole : window.sessionStorage.getItem('role')||'1'
        }
    },
    name: 'Admin',
    mounted() {
    },
    methods: {
        logout () {
            // 清空sessionStorage中的token
            window.sessionStorage.clear()
            // 跳转到登录页面
            this.$router.push('/login')
            this.$message.success('已安全退出')
        },
        /**
         * 折叠侧边栏
         */
        toggleCollapsed () {
            this.isCollapsed = !this.isCollapsed
        },
    }
}
</script>

<style lang="less" scoped>

.home-container {
    height: 100vh;
}

.el-header{
    background-color: #67C23A;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fff;
    font-size: 20px;
    > div {
        display: flex;
        align-items: center;
        img{
            width: 60px;
        }
        span {
            margin-left: 15px;
        }
    }
}

.el-aside{
    background-color: #a4ee80;
    .el-menu {
        border-right: none;
    }
}

.el-main{
    background-color: #eaedf1;
}

.iconfont {
    margin-right: 10px;
}

.toggle-button {
    background-color: #a4ee80;
    font-size: 10px;
    line-height: 24px;
    color: #ffffff;
    text-align: center;
    letter-spacing: 0.2em;
    // cursor CSS 属性设置鼠标光标的类型（如果有），以在鼠标指针悬停在元素上时显示
    cursor: pointer;
}
</style>
