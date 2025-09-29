<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">商品模块</a></el-breadcrumb-item>
            <el-breadcrumb-item>商品分类</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入内容">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button type="primary" style="margin-left: 10px" @click="openForm" plain>添加</el-button>
            <el-button type="danger" @click="handleDelall" plain>批量删除</el-button>
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
                prop="commoditTypeName"
                label="名称">
            </el-table-column>
            <el-table-column
                label="操作"
                width="300">
                <template slot-scope="scope">
                    <el-button @click="editForm(scope.row)" type="text" size="small">修改</el-button>
                    <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDel(scope.row)">
                        <el-button type="text" size="small" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!-- 弹出框 -->
        <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" center width="30%">
            <el-form ref="Form" :model="form" :rules="rules" label-width="100px">
                <el-form-item prop="name" label="分类名称" >
                    <el-input v-model="form.commoditTypeName" autocomplete="off"></el-input>
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
                commoditTypeName:null
            },
            rules: {
                commoditTypeName: [
                    { required: true, message: '请输入分类名称', trigger: 'blur' }
                ]
            }
        }
    },
    methods:{
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        openForm(){
            this.formTitle = '添加分类信息'
            this.dialogFormVisible = true
        },
        clearForm(){
            this.$refs.Form.resetFields()
            this.form.id = null
            this.form.commoditTypeName = ''
            this.dialogFormVisible = false
        },
        editForm(row){
            this.formTitle = '修改分类信息'
            this.form.id = row.id
            this.form.commoditTypeName = row.commoditTypeName
            this.dialogFormVisible = true
        },
        submitForm(){
            this.$refs.Form.validate(async (valid) => {
                if (valid) {
                    if (this.formTitle === '添加分类信息') {
                        let res = await request.addCommodityClass(this.form)
                        if (res){
                            this.$message.success('添加成功')
                            await this.getData()
                            this.clearForm()
                        }
                    } else {
                        let res = await request.editCommodityClass(this.form)
                        if (res){
                            this.$message.success('修改成功')
                            await this.getData()
                            this.clearForm()
                        }
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        async handleDel(row){
            let param = row.id
            let res = await request.delCommodityClassById(param)
            if (res) {
                await this.getData()
                this.$message.success('执行成功')
            }
        },
        async handleDelall(){
            if (this.multipleSelection.length===0){
                this.$message.error('请勾选后批量删除')
                return
            }
            await this.$alert('删除后无法恢复，确认删除嘛？', '提示', {
                confirmButtonText: '确定',
                callback: async action => {
                    if (action === 'confirm') {
                        let param = this.multipleSelection.map(item => {
                            return item.id
                        })
                        let res = await request.delAllCommodityClassById(param)
                        if (res !== null) {
                            await this.getData()
                            this.$message.success('执行成功')
                        }
                    }
                }
            });
        },
        async getData() {
            this.loading = true
            let res = await request.getCommodityClass()
            if (res){
                this.tableData = res.data.comodityList
                this.loading = false
            }
        }
    },
    computed:{
        dataList(){
            let filList = this.tableData
            return filList.filter(p=>{
                return p.commoditTypeName.indexOf(this.input) !== -1
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
