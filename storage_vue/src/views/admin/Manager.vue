<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/admin/manager">权限模块</a></el-breadcrumb-item>
            <el-breadcrumb-item>管理员列表</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入名称">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button type="primary" style="margin-left: 10px" @click="openForm" plain>添加</el-button>
            <el-button type="danger" @click="handleDelAll" plain>批量删除</el-button>
        </div>
        <el-table :loading="loading" border @selection-change="handleSelectionChange" :data="dataList"
                  style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="userName" label="用户名">
            </el-table-column>
            <el-table-column prop="password" label="密码">
            </el-table-column>
            <el-table-column prop="classifyName" label="身份类型">
                <template slot-scope="scope">
                    <el-link type="primary" size="mini">{{scope.row.classifyname}}</el-link>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-button type="text" @click="editForm(scope.row)" size="small" slot="reference">修改</el-button>
                    <el-popconfirm title="确定删除该用户嘛?" @confirm="handleDel(scope.row)">
                        <el-button type="text" size="small" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!-- 弹出框 -->
        <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" center width="30%">
            <el-form ref="Form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="登入名" prop="userName">
                    <el-input type="text" v-model="form.userName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="text" v-model="form.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="classifyId" label="用户类型" >
                    <el-select v-model="form.classifyId" placeholder="请选择" style="width: 100%">
                        <el-option
                            v-for="item in classifylist"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="clearForm">取 消</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "User",
    data(){
        return{
            tableData:[],
            input:'',
            multipleSelection:[],
            dialogFormVisible:false,
            formTitle:'',
            loading:false,
            form:{
                id:null,
                userName:null,
                password:null,
                classifyId:null
            },
            classifylist:[],
            rules: {
                userName: [
                    {required: true, message: '请输入用户名', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '密码不能为空', trigger: 'blur'},
                ],
                classifyId: [
                    {required: true, message: '请选择权限类型', trigger: 'change'},
                ],
            }
        }
    },
    computed:{
        dataList(){
            let filList = this.tableData
            return filList.filter(p=>{
                return p.userName.indexOf(this.input) !== -1
            })
        }
    },
    methods:{
        openForm(){
            this.formTitle = '添加管理员'
            this.dialogFormVisible = true
        },
        clearForm(){
            this.$refs.Form.resetFields()
            this.dialogFormVisible = false
        },
        editForm(row){
            this.formTitle = '修改管理员信息'
            this.form.id = row.id
            this.form.userName = row.userName
            this.form.password = row.password
            this.form.classifyId = row.classifyId
            this.dialogFormVisible = true
        },
        submitForm(){
            this.$refs.Form.validate(async (valid) => {
                if (valid) {
                    if (this.formTitle === '添加管理员') {
                        let res = await request.postAdmin(this.form)
                        if (res){this.$message.success('添加成功')}
                        this.clearForm()
                        await this.getData()
                    } else {
                        let res = await request.putAdmin(this.form)
                        if (res){this.$message.success('修改成功')}
                        this.clearForm()
                        await this.getData()
                    }
                } else {
                    return false;
                }
            });
        },
        handleSelectionChange(val){
            this.multipleSelection = val
        },
        async handleDel(row){
            let param = [{id:row.id}]
            let res = await request.deleteAdmin(param)
            if (res){this.$message.success('删除成功')}
            await this.getData()
        },
        handleDelAll(){
            if (this.multipleSelection.length===0){
                this.$message.error('请勾选后批量删除')
                return
            }
            this.$alert('删除后无法恢复，确认删除嘛？', '提示', {
                confirmButtonText: '确定',
                callback: async action => {
                    if (action === 'confirm') {
                        let param = this.multipleSelection.map(item => {
                            return {id: item.id}
                        })
                        let res = await request.deleteAdmin(param)
                        if (res) {
                            this.$message.success('删除成功')
                        }
                        await this.getData()
                    }
                }
            });
        },
        async getClass() {
            this.classifylist = await request.getRole()
        },
        async getData(){
            this.loading = true
            this.tableData = await request.getAdmin()
            this.loading = false
        }
    },
    mounted() {
        this.getClass()
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
