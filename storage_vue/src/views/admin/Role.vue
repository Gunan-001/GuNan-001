<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/admin/manager">权限模块</a></el-breadcrumb-item>
            <el-breadcrumb-item>角色管理</el-breadcrumb-item>
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
            @selection-change="handleSelectionChange">
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
                prop="name"
                label="名称">
            </el-table-column>
            <el-table-column
                prop="account"
                label="描述">
            </el-table-column>
            <el-table-column
                label="操作">
                <template slot-scope="scope">
                    <el-button @click="editForm(scope.row)" type="text" size="small">修改</el-button>
                    <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDel(scope.row)">
                        <el-button type="text" size="small" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
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
            multipleSelection:[],
            loading:false,
        }
    },
    methods:{
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        openForm(){
            this.$message.warning('该模块暂不支持添加信息')
        },
        editForm(){
            this.$message.warning('该模块暂不支持修改信息')
        },
        submitForm(){
            this.$message.warning('此模块暂不支持修改')
        },
        handleDel(row){
            this.$message.warning('此模块暂不支持删除')
            let param = [{id:row.id}]
            console.log(param)
        },
        handleDelall(){
            this.$message.warning('此模块暂不支持删除')
        },
        async getData() {
            this.loading = true
            this.tableData = await request.getRole()
            this.loading = false
        }
    },
    computed:{
        dataList(){
            let filList = this.tableData
            return filList.filter(p=>{
                return p.name.indexOf(this.input) !== -1
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
