<template>
    <div class="login_container clearfix">
        <div class="login_box">
            <!-- 头像 -->
            <div class="avatar_box">
                <img src="../../assets/logo.png" >
            </div>
            <!-- 登录 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
                <!-- 用户名 -->
                <el-form-item prop="account">
                    <el-input v-model="loginForm.account" placeholder="请输入用户名" prefix-icon="el-icon-user-solid"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" placeholder="请输入密码" prefix-icon="el-icon-lock" type="password"></el-input>
                </el-form-item>
                <!-- 按钮区域 -->
                <el-form-item class="btns">
                    <el-button type="success" @click="login">登录</el-button>
                    <el-button type="info" @click="resetLoginForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: 'Login',
    data () {
        return {
            // 这是登录表单的数据绑定对象
            loginForm: {
                account: null,
                password: null
            },
            // 这是表单的验证规则对象
            loginFormRules: {
                account: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        // 点击重置按钮，重置登录表单
        resetLoginForm () {
            this.$refs.loginFormRef.resetFields()
        },
        login () {
            this.$refs.loginFormRef.validate(async (valid) => {
                if (valid) {
                    let res = await request.manageLogin(this.loginForm)
                    if (res){
                        this.saveInfo(res.data.account,res.data.token,'manage')
                        await this.$router.push('/admin/goods')
                        this.$message.success('登入成功')
                    }
                } else {
                    return false;
                }
            });
        },
        saveInfo(account, token, role){
            sessionStorage.setItem('account',account)
            sessionStorage.setItem('token',token)
            sessionStorage.setItem('role',role)
        }
    },
    mounted() {
        this.$store.dispatch('removeUserInfo')
    }
}

</script>

<style lang="less" scoped>//加上scoped只在组件内生效，去掉则样式会全局生效
.login_container{
    background-color: #67C23A;
    height: 100vh;
    width: 100vw;
}

.login_box{
    width: 450px;
    height: 300px;
    background-color: #fff;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}

.avatar_box{
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
    }
}

.login_form{
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
}

.btns{
    display: flex;
    justify-content: flex-end;
}
</style>
