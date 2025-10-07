<template>
    <div class="contain-main-bar">
        <!--  顶部导航栏-start  -->
        <div class="nav-top-bar">
            <div class="nav-btn">
                <ul>
                    <li v-if="nickName===''||nickName===null">
                        <el-button type="text" @click="open('login')">登录</el-button>
                        <span class="sep"> | </span>
                        <el-button type="text" @click="open('register')">注册</el-button>
                        <span class="sep"> | </span>
                        <el-button type="text" @click="open('password')">忘记密码</el-button>
                    </li>
                    <li v-else>
                        <span style="color: #B0B0B0">欢迎,</span>
                        <el-popover placement="top" width="180" v-model="visible">
                            <p>确定退出登录吗？</p>
                            <div style="text-align: right; margin: 10px 0 0">
                                <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                                <el-button type="primary" size="mini" @click="logout">确定</el-button>
                            </div>
                            <el-button type="text" slot="reference">{{nickName}}</el-button>
                        </el-popover>
                    </li>
                    <li>
                        <router-link to="/mall/order">我的订单</router-link>
                    </li>
                    <li>
                        <router-link to="/mall/collect">我的收藏</router-link>
                    </li>
                    <li :class="$store.state.shopNum > 0 ? 'shopCart-full' : 'shopCart'">
                        <router-link to="/mall/shopcar">
                            <i class="el-icon-shopping-cart-full"></i> 购物车
                            <span class="num">({{ $store.state.shopNum }})</span>
                        </router-link>
                    </li>
                </ul>
                <!--  登入注册弹出框  -->
                <el-dialog
                    :title="formTitle"
                    :visible.sync="$store.state.loginDialogVisible"
                    :width="formTitle==='登录'?'15%':'20%'"
                    @closed="clearForm"
                    center>
                    <el-form ref="Form" :model="form" :rules="rules" status-icon>
                        <el-form-item prop="userName">
                            <el-input
                                prefix-icon="el-icon-user-solid"
                                type="text"
                                placeholder="请输入账号"
                                v-model="form.userName">
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="userPassword">
                            <el-input
                                prefix-icon="el-icon-lock"
                                type="password"
                                placeholder="请输入密码"
                                v-model="form.userPassword">
                            </el-input>
                        </el-form-item>
                        <el-form-item v-if="formTitle==='注册'" prop="nickName">
                            <el-input
                                prefix-icon="el-icon-paperclip"
                                type="text"
                                placeholder="请输入昵称"
                                v-model="form.nickName">
                            </el-input>
                        </el-form-item>
                        <el-form-item v-if="formTitle==='注册'" prop="userPhone">
                            <el-input
                                prefix-icon="el-icon-phone"
                                type="text"
                                placeholder="请输入联系方式"
                                v-model="form.userPhone">
                            </el-input>
                        </el-form-item>
                        <el-form-item v-if="formTitle==='注册'" prop="userSex">
                            <div>
                                <el-radio v-model="form.userSex" label="男">男</el-radio>
                                <el-radio v-model="form.userSex" label="女">女</el-radio>
                            </div>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button style="width: 100%" type="primary" @click="submit">确 定</el-button>
                  </span>
                </el-dialog>

                <!-- 忘记密码弹窗 -->
<el-dialog
  title="忘记密码"
  :visible.sync="forgotVisible"
  width="22%"
  center
  @closed="clearForgotForm">
  <el-form ref="forgotForm" :model="forgotForm" :rules="forgotRules" status-icon>
    <!-- 用户名输入 -->
    <el-form-item prop="userName">
      <el-input
        prefix-icon="el-icon-user-solid"
        type="text"
        placeholder="请输入您的账号"
        v-model="forgotForm.userName">
      </el-input>
    </el-form-item>
    <!-- 手机号输入 -->
    <el-form-item prop="userPhone">
      <el-input
        prefix-icon="el-icon-phone"
        type="text"
        placeholder="请输入注册时的手机号"
        v-model="forgotForm.userPhone">
      </el-input>
    </el-form-item>
    <!-- 新密码输入 -->
    <el-form-item prop="newPassword">
      <el-input
        prefix-icon="el-icon-lock"
        type="password"
        placeholder="请输入新密码"
        v-model="forgotForm.newPassword">
      </el-input>
    </el-form-item>
    <!-- 确认新密码输入 -->
    <el-form-item prop="confirmPassword">
      <el-input
        prefix-icon="el-icon-lock"
        type="password"
        placeholder="请再次输入新密码"
        v-model="forgotForm.confirmPassword">
      </el-input>
    </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="forgotVisible = false">取消</el-button>
    <el-button type="primary" @click="submitForgotPassword">确定修改</el-button>
  </span>
</el-dialog>
            </div>
        </div>
        <!--  顶部导航栏-end  -->
        <div class="middle-bar">
            <!--  顶部菜单栏-start  -->
            <div class="header-wapper">
                <!--  logo -->
                <div class="logo-bar">
                    <img src="@/assets/logo.png">
                    <span class="logotext" style="color: #e3b00b">蔬</span>
                    <span class="logotext" style="color: #8c1dd2">果</span>
                    <span class="logotext" style="color: #21e30b">购</span>
                </div>
                <!--  导航栏 -->
                <div class="nav-button">
                    <el-menu :default-active="activeIndex" router class="el-menu-demo" mode="horizontal">
                        <el-menu-item index="/mall/index">首页</el-menu-item>
                        <el-menu-item index="/mall/allgoods" >全部商品</el-menu-item>
                        <el-menu-item index="/mall/leavemessage">留言板</el-menu-item>
                    </el-menu>
                </div>
                <!--  搜索框 -->
                <div class="nav-input-bar" v-if="$route.path==='/mall/index'|| $route.path==='/mall/allgoods'">
                    <el-input placeholder="请输入内容" v-model="input" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
                    </el-input>
                </div>
                <!-- 替补搜索框 -->
                <div v-else class="nav-input-bar"></div>
            </div>
            <!--  顶部菜单栏-end  -->
            <!--  主体-start  -->
            <router-view></router-view>
            <!--  主体-end  -->
        </div>
        <!--  底部导航栏-start  -->
        <div class="footer">
            <div class="ng-promise-box">
                <div class="ng-promise">
                    <p class="text">
                        <a class="icon1" href="javascript:;">7天无理由退换货</a>
                        <a class="icon2" href="javascript:;">满99元全场免邮</a>
                        <a class="icon3" style="margin-right: 0" href="javascript:;">100%品质保证</a>
                    </p>
                </div>
            </div>

            <div class="mod_help">
                <p>
                    <router-link to="/">首页</router-link>
                    <span>|</span>
                    <router-link to="/mall">全部商品</router-link>
                    <span>|</span>
                    <router-link to="/mall">关于我们</router-link>
                </p>
                <p class="coty">商城版权所有 &copy; 2023-2024</p>
            </div>
        </div>
        <!--  底部导航栏-end  -->
    </div>
</template>

<script>
import request from "@/utils/request";
export default {
    name: "index",
    data() {
        // 自定义密码校验器
        const validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,18}$/.test(value)) {
                callback(new Error('密码长度为5-18位，且必须包含字母和数字'));
            } else {
                callback();
            }
        };
  // 忘记密码-新密码校验（支持确认密码联动）
  const validateNewPass = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('请输入新密码'));
    } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,18}$/.test(value)) {
      callback(new Error('密码长度为5-18位，且必须包含字母和数字'));
    } else {
      // 已输入确认密码时，触发确认密码校验
      if (this.forgotForm.confirmPassword !== '') {
        this.$refs.forgotForm && this.$refs.forgotForm.validateField('confirmPassword');
      }
      callback();
    }
  };

  // 忘记密码-确认密码校验
  const validateConfirmPass = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('请再次输入新密码'));
    } else if (value !== this.forgotForm.newPassword) {
      callback(new Error('两次输入的密码不一致'));
    } else {
      callback();
    }
  };
        return {
            activeIndex: '',
            nickName:'',
            input: '',
            visible: false,
            formTitle:'登录',
            form:{
                userName:'',
                userPassword:'',
                nickName:'',
                userPhone:'',
                userSex:''
            },
            forgotVisible: false, // 忘记密码弹窗显示状态
    // 忘记密码表单数据
    forgotForm: {
      userName: '',    // 用户名
      userPhone: '',   // 手机号
      newPassword: '', // 新密码
      confirmPassword: '' // 确认新密码
    },
            // 表单校验规则
            rules: {
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' },
                    { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9_]{5,18}$/, message: '支持中英文、数字和下划线', trigger: 'blur' }
                ],
                userPassword: [
                    { required: true, validator: validatePass, trigger: 'blur' }
                ],
                nickName: [
                    { required: true, message: '请输入昵称', trigger: 'blur' },
                    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' },
                    { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{2,10}$/, message: '支持中英文和数字', trigger: 'blur' }
                ],
                userPhone: [
                    { required: true, message: '请输入手机号码', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
                ],
                userSex: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ]
            },
            
            forgotRules: {
      userName: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 18, message: '长度在 5 到 18 个字符', trigger: 'blur' },
        { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9_]{5,18}$/, message: '支持中英文、数字和下划线', trigger: 'blur' }
      ],
      userPhone: [
        { required: true, message: '请输入手机号码', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
      ],
      newPassword: [
        { required: true, validator: validateNewPass, trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, validator: validateConfirmPass, trigger: 'blur' }
      ]
    }



        }
    },
    methods: {

        /* 清空弹出框 */
        clearForm(){
            // 使用 resetFields() 清空并重置校验状态
            this.$refs.Form && this.$refs.Form.resetFields();
            this.formTitle = '登录';
            this.$store.state.loginDialogVisible = false;
        },
        /* 提交 */
        async submit() {
            this.$refs.Form.validate(async (isValid) => {
                if (!isValid) {
                    return; // 校验不通过，终止提交
                }
                // 校验通过，继续执行登录或注册逻辑
                if (this.formTitle === '登录') {
                    let param = {userName: this.form.userName, userPassword: this.form.userPassword};
                    let res = await request.login(param);
                    if (res && res.data) {
                        let data = res.data;
                        this.saveInfo(data.userId, data.userName, data.nickName, data.token);
                        this.nickName = data.nickName || data.userName;
                        this.clearForm();
                        this.$message.success(res.message || '登录成功');
                        await this.getShopCar();
                        this.$nextTick(()=>{
                            this.$router.push('/mall/index');
                        });
                    }
                } else {
                    let res = await request.register(this.form);
                    if (res){
                        this.$message.success('注册成功');
                        this.clearForm();
                        setTimeout(() => {
                            this.open('login');
                        }, 1000);
                    }
                }
            });
        },
        open(attr) {
        // 处理忘记密码
        if (attr === 'password') {
            this.forgotVisible = true;
            this.$refs.forgotForm && this.$refs.forgotForm.resetFields();
            return;
        }
        // 处理登录/注册
        this.formTitle = attr === 'login' ? '登录' : '注册';
        this.$store.state.loginDialogVisible = true;
    },

  /* 清空忘记密码表单 */
  clearForgotForm() {
    this.$refs.forgotForm && this.$refs.forgotForm.resetFields();
    this.forgotVisible = false;
  },

  /* 提交忘记密码修改（调用指定接口） */
  async submitForgotPassword() {
    this.$refs.forgotForm.validate(async (isValid) => {
      if (!isValid) return; // 表单校验不通过，终止

      try {
        // 构造接口参数（与后端接口字段匹配）
        const params = {
          userName: this.forgotForm.userName,
          userPhone: this.forgotForm.userPhone,
          userPassword: this.forgotForm.newPassword // 新密码字段（后端若用其他名需同步修改）
        };

        // 调用忘记密码接口（需确保 request 中已定义 updataPasswordByNameAndPhone 方法）
        const res = await request.updataPasswordByNameAndPhone(params);
        
        if (res) {
          this.$message.success(res.message || '密码修改成功！');
          this.forgotVisible = false; // 关闭弹窗
          setTimeout(() => {
            this.open('login'); // 自动跳转登录
          }, 1500);
        }
      } catch (error) {
        this.$message.error(error.message || '密码修改失败，请重试');
      }
    });
  },
        /* 保存信息至sessionstorage */
        saveInfo(userId,userName,nickName,token){
            sessionStorage.setItem('userId', userId);
            sessionStorage.setItem('userName', userName);
            sessionStorage.setItem('nickName', nickName);
            sessionStorage.setItem('token', token);
        },
        /* 退出登录 */
        logout(){
            this.$store.dispatch('removeUserInfo');
            this.$message.success('账号已安全退出');
            this.nickName = '';
            this.$nextTick(()=>{
                this.$router.push('/mall/index');
            });
        },
        /* 搜索跳转 */
        search(){
            if(this.input.trim()){
                this.$router.push({path: '/mall/allgoods', query: {kwd: this.input}});
            } else {
                this.$message.warning('请输入搜索内容');
            }
        },
        /* 获取购物车商品数量 */
        async getShopCar() {
            await this.$store.dispatch('getShopNum');
        }
    },
    mounted() {
        this.activeIndex = sessionStorage.getItem('activePath') || '/mall/index';
        if (sessionStorage.getItem('token')){
            this.getShopCar();
        }
        let name = sessionStorage.getItem('nickName') || sessionStorage.getItem('userName');
        this.nickName = name || '';
        this.input = this.$route.query.kwd || '';
        sessionStorage.removeItem('account');
    },
    watch: {
        // 监听路由变化，同步搜索框内容
        '$route.query.kwd'(newVal) {
            this.input = newVal || '';
        }
    }
}
</script>

<style lang="less">
ul {
    padding: 0;
    margin: 0;
}
/* 首页 */
.contain-main-bar {
    display: flex;
    flex-flow: column nowrap;
    /* 顶部容器 */
    .nav-top-bar {
        height: 40px;
        width: 100%;
        background-color: #3D3D3D;
        margin-bottom: 20px;
        /* 顶部导航栏容器 */
        .nav-btn {
            width: 1225px;
            height: 40px;
            line-height: 40px;
            margin: 0 auto;
            display: flex;
            flex-flow: row nowrap;
            justify-content: flex-end;
            /* 导航按钮 */
            ul {
                font-size: 14px;
                display: flex;
                flex-flow: row nowrap;

                li {
                    margin-left: 20px;

                    a {
                        color: #B0B0B0;
                        text-decoration: none;
                    }

                    a:hover {
                        text-decoration: none;
                        color: #ffffff;
                    }

                    button, .sep {
                        color: #B0B0B0
                    }

                    button:hover, .sep:hover {
                        color: #ffffff;
                    }
                }
                /* 购物车按钮 */
                .shopCart-full,.shopCart{
                    width: 120px;
                }
                .shopCart-full a {
                    color: white;
                }
                .shopCart:hover a,
                .shopCart-full:hover a{
                    display: block;
                    height: 40px;
                    color: #ff6700;
                    background-color: #ffffff;
                }
            }
        }
    }

    /* 页面主体 */
    .middle-bar {
        margin: 0 auto;
        width: 100%;
        /* 控制台容器 */
        .header-wapper {
            width: 1225px;
            height: 60px;
            margin: 0 auto;
            display: flex;
            flex-flow: row nowrap;
            justify-content: space-between;
            border-bottom: 1px #e6e6e6 solid;
            /* logo容器 */
            .logo-bar {
                width: 180px;
                height: 60px;
                display: flex;
                flex-flow: row nowrap;
                justify-content: flex-start;
                cursor: pointer;

                img {
                    width: 60px;
                }

                .logotext {
                    width: 40px;
                    font-size: 30px;
                    padding-top: 8px;
                    text-align: center;
                    vertical-align: center;
                }
            }
            .nav-button {
                width: 550px;
            }
            /* 输入框 */
            .nav-input-bar {
                width: 300px;
                line-height: 60px;
            }
        }
    }

    /* 登入注册弹出框 */
    .el-dialog{
        .el-dialog__header{
            button{
                margin-top: -10px;
                margin-right: -5px;
            }
        }
        .el-dialog__body {
            padding: 0 20px;
            color: #606266;
            font-size: 14px;
        }
        .el-dialog__footer {
            padding-top: 0;
            padding-bottom: 40px;
        }
    }

    /* 底部导航栏 */
    .footer {
        width: 100%;
        text-align: center;
        background: #2f2f2f;
        padding-bottom: 20px;
        .ng-promise-box {
            border-bottom: 1px solid #3d3d3d;
            line-height: 145px;
        }
        .ng-promise-box {
            margin: 0 auto;
            border-bottom: 1px solid #3d3d3d;
            line-height: 145px;
        }
        .ng-promise-box .ng-promise .text {
            margin: 0;
        }
        .ng-promise-box .ng-promise p a {
            color: #fff;
            font-size: 20px;
            margin-right: 210px;
            padding-left: 44px;
            height: 40px;
            display: inline-block;
            line-height: 40px;
            text-decoration: none;
            background: url("../assets/us-icon.png") no-repeat left 0;
        }
        .github {
            height: 50px;
            line-height: 50px;
            margin-top: 20px;
        }
        .github .github-but {
            width: 50px;
            height: 50px;
            margin: 0 auto;
            background: url("../assets/github.png") no-repeat;
        }
        .mod_help {
            text-align: center;
            color: #888888;
        }
        .mod_help p {
            margin: 20px 0 16px 0;
        }
        .mod_help p a {
            color: #888888;
            text-decoration: none;
        }
        .mod_help p a:hover {
            color: #fff;
        }
        .mod_help p span {
            padding: 0 22px;
        }
    }
}
</style>
