<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/admin/banner">系统设置</a></el-breadcrumb-item>
            <el-breadcrumb-item>留言板管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入内容">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button type="danger" style="margin-left: 10px" @click="handleDelAll" plain>批量删除</el-button>
        </div>
        <el-table
            :data="dataList"
            border
            :loading="loading"
            @selection-change="handleSelectionChange"
            :max-height="tableHeight"
            style="width: 100%">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column label="序号" width="60">
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column
                prop="userName"
                label="用户名">
            </el-table-column>
            <el-table-column
                prop="userPhone"
                label="联系方式">
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="messageInfo"
                label="用户留言">
            </el-table-column>
            <el-table-column
                prop="messageTime"
                label="留言时间">
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="shopBack"
                label="商家回复">
            </el-table-column>
            <el-table-column
                label="操作">
                <template slot-scope="scope">
                    <el-button @click="editForm(scope.row)" type="text" size="small">回复</el-button>
                    <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDel(scope.row)">
                        <el-button type="text" size="small" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!-- 弹出框 -->
        <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" center width="30%">
            <el-form ref="Form" :model="form" :rules="rules" label-width="100px">
                <el-form-item prop="messageInfo" label="用户留言" >
                    <el-input
                        type="textarea"
                        disabled
                        autosize
                        resize="none"
                        v-model="form.messageInfo">
                    </el-input>
                </el-form-item>
                <el-form-item prop="messageTime" label="留言时间" >
                    <el-input disabled v-model="form.messageTime" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="shopBack" label="用户留言" >
                    <el-input
                        type="textarea"
                        autosize
                        resize="none"
                        placeholder="请回复"
                        v-model="form.shopBack">
                    </el-input>
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
    name: "GoodsType",
    data() {
        return {
            tableData: [],
            input: '',
            tableHeight:null,
            formTitle:'',
            dialogFormVisible:false,
            multipleSelection:[],
            loading:false,
            form:{
                id:null,
                messageInfo:null,
                messageTime:null,
                shopBack:null,
            },
            rules: {
                shopBack: [
                    { required: true, message: '回复内容不能为空', trigger: 'blur'},
                ]
            }
        }
    },
    methods:{
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        clearForm(){
            this.form.id = null
            this.form.messageInfo = null
            this.form.shopBack = null
            this.form.messageTime = null
            this.$refs.Form.resetFields()
            this.dialogFormVisible = false
        },
        editForm(row){
            this.formTitle = '回复留言'
            this.form.id = row.id
            this.form.messageInfo = row.messageInfo
            this.form.shopBack = row.shopBack
            this.form.messageTime = row.messageTime
            this.dialogFormVisible = true
        },
        submitForm(){
            this.$refs.Form.validate(async (valid) => {
                if (valid) {
                    let params = this.form
                    params.messageTime = null
                    let res = await request.editMessageInfo(params)
                    if (res){
                        this.$message.success('回复成功')
                        await this.clearForm()
                        await this.getData()
                    }
                } else {
                    return false;
                }
            });
        },
        async handleDel(row) {
            let param = [row.id]
            let res = await request.delAllMessageInfo(param)
            if (res) {
                this.$message.success('执行成功')
                await this.getData()
            }
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
                            return item.id
                        })
                        let res = await request.delAllMessageInfo(param)
                        if (res) {
                            this.$message.success('执行成功')
                            await this.getData()
                        }
                    }
                }
            });
        },
        async getData() {
            this.loading = true
            let res = await request.getMessageInfo()
            if (res){
                this.tableData = res.data.boardList
                this.loading = false
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
    mounted() {
        this.tableHeight = document.querySelector('.box-card').clientHeight-145
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
