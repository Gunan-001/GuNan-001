<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/admin/user">用户模块</a></el-breadcrumb-item>
            <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入名称  ">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button style="margin-left: 10px" type="primary" @click="handleAllUser(0)" plain>批量解除禁用</el-button>
            <el-button type="warning" @click="handleAllUser(1)" plain>批量禁用</el-button>
        </div>
        <el-table :loading="loading" border @selection-change="handleSelectionChange" :data="dataList" style="width: 100%;"
            :max-height="tableHeight">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                    {{ scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column prop="userName" label="用户名">
            </el-table-column>
            <el-table-column prop="nickName" label="昵称">
            </el-table-column>
            <el-table-column prop="userSex" label="性别">
            </el-table-column>
            <el-table-column prop="userPhone" label="联系电话">
            </el-table-column>
            <el-table-column prop="userStatus" label="身份状态">
                <template slot-scope="scope">
                    <el-link v-if="scope.row.userStatus === 0" type="primary" size="mini">正常</el-link>
                    <el-link v-if="scope.row.userStatus === 1" type="info" size="mini">已禁用</el-link>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm title="确定禁用该用户嘛?" @confirm="handleUser(scope.row)">
                        <el-button type="text" size="small" slot="reference">禁用</el-button>
                    </el-popconfirm>
                    &nbsp;&nbsp;
                    <el-popconfirm title="确定解除禁用该用户嘛?" @confirm="handleUserEnable(scope.row)">
                        <el-button type="text" size="small" slot="reference">解除禁用</el-button>
                    </el-popconfirm>
                    &nbsp;&nbsp;
                    <el-button type="text" size="small" @click="showPasswordDialog(scope.row)">修改密码</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 密码修改弹窗 -->
        <el-dialog title="修改密码" :visible.sync="passwordDialogVisible" width="30%">
            <el-form :model="passwordForm" :rules="passwordRules" ref="passwordForm">
                <el-form-item label="新密码" prop="newPassword">
                    <el-input type="password" v-model="passwordForm.newPassword" placeholder="请输入新密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input type="password" v-model="passwordForm.confirmPassword" placeholder="请再次输入新密码"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="passwordDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="confirmPasswordChange">确定</el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "User",
    data() {
        // 密码校验规则
        const validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else if (value.length < 6) {
                callback(new Error('密码长度不能少于6位'));
            } else {
                if (this.passwordForm.confirmPassword !== '') {
                    this.$refs.passwordForm.validateField('confirmPassword');
                }
                callback();
            }
        };
        const validateConfirmPassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.passwordForm.newPassword) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };

        return {
            tableData: [],
            input: '',
            tableHeight: null,
            multipleSelection: [],
            loading: false,
            // 密码弹窗相关
            passwordDialogVisible: false,
            currentUserId: null,
            passwordForm: {
                newPassword: '',
                confirmPassword: ''
            },
            passwordRules: {
                newPassword: [
                    { validator: validatePassword, trigger: 'blur' }
                ],
                confirmPassword: [
                    { validator: validateConfirmPassword, trigger: 'blur' }
                ]
            }
        }
    },
    computed: {
        dataList() {
            let filList = this.tableData
            return filList.filter(p => {
                return p.userName.indexOf(this.input) !== -1
            })
        }
    },
    methods: {
        handleSelectionChange(val) {
            this.multipleSelection = val
        },
        // 禁用会员
        async handleUser(row) {
            let param = [{
                id: row.id,
                userStatus: 1 	//1为禁用  0为正常
            }]
            let res = await request.editUserInfo(param)
            if (res) {
                this.$message.success('修改成功')
                await this.getData()
            }
        },
        // 解除禁用用户
        async handleUserEnable(row) {
            let param = [{
                id: row.id,
                userStatus: 0 	// 0为正常（解除禁用）
            }]
            let res = await request.editUserInfo(param)
            if (res) {
                this.$message.success('解除禁用成功')
                await this.getData()
            }
        },
        // 显示密码修改弹窗
        showPasswordDialog(row) {
            this.currentUserId = row.id;
            // 重置表单
            this.$refs.passwordForm && this.$refs.passwordForm.resetFields();
            this.passwordDialogVisible = true;
        },
        // 确认修改密码
        // 确认修改密码的方法中，将数组改为单个对象
        async confirmPasswordChange() {
            this.$refs.passwordForm.validate(async (valid) => {
                if (valid) {
                    try {
                        // 重点：去掉外层数组，直接传递对象
                        let param = {
                            id: this.currentUserId,
                            userPassword: this.passwordForm.newPassword
                        }
                        let res = await request.updataPassword(param) // 传递单个对象
                        if (res) {
                            this.$message.success('修改密码成功')
                            this.passwordDialogVisible = false;
                            await this.getData()
                        }
                    } catch (error) {
                        this.$message.error('修改密码失败，请重试')
                    }
                } else {
                    return false;
                }
            });
        },
        // 批量操作
        handleAllUser(state) {
            if (this.multipleSelection.length === 0) {
                this.$message.error('请勾选后操作')
                return false
            }
            this.$confirm(`确定${state === 1 ? '禁用' : '解除禁用'}已勾选用户?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                let param = this.multipleSelection.map(item => {
                    return { id: item.id, userStatus: state }
                })
                let res = await request.editUserInfo(param)
                if (res) {
                    this.$message.success('修改成功')
                    await this.getData()
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
        async getData() {
            this.loading = true
            let res = await request.getUserData()
            if (res) {
                this.tableData = res.data.personInfoList
                this.loading = false
            }
        }
    },
    mounted() {
        this.tableHeight = document.querySelector('.box-card').clientHeight - 145
        this.getData()
    }
}
</script>

<style lang="less" scoped>
.box-card {
    height: calc((100vh) - (102px));
}

.top-control-group {
    margin-top: 25px;
    margin-bottom: 20px;
    height: 40px;
    display: flex;
    flex-flow: row nowrap;

    .el-input {
        width: 250px;
        height: 40px;
    }
}
</style>
