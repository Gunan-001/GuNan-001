<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/admin/banner">系统设置</a></el-breadcrumb-item>
            <el-breadcrumb-item>轮播图管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入内容">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button type="primary" style="margin-left: 10px" @click="openForm" plain>添加</el-button>
            <el-button type="danger" @click="handleDelAll" plain>批量删除</el-button>
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
                    {{ scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                prop="name"
                label="图片名称">
            </el-table-column>
            <el-table-column
                prop="path"
                label="图片">
                <template slot-scope="scope">
                    <div class="imgshow">
                        <img :src="baseUrl+scope.row.path" @click="showImg(scope.row)">
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="editForm(scope.row)" slot="reference">修改</el-button>
                    <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDel(scope.row)">
                        <el-button type="text" size="small" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!-- 弹出框 -->
        <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" center width="30%">
            <el-form ref="Form" :model="form" :rules="rules" label-width="100px">
                <el-form-item prop="name" label="商品名称">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="path" label="轮播图图片">
                    <el-upload
                        class="avatar-uploader"
                        :action=upLoadUrl
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="form.path" :src="baseUrl+form.path" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
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
    name: "Banner",
    data() {
        return {
            tableData: [],
            input: '',
            formTitle:'',
            tableHeight: null,
            dialogFormVisible: false,
            multipleSelection: [],
            loading: false,
            upLoadUrl: request.upLoadUrl,
            baseUrl:request.baseUrl,
            form: {
                id: null,
                name: null,
                path: null,
            },
            rules: {
                name: [
                    {required: true, message: '请输入商品名称', trigger: 'blur'}
                ],
                path: [
                    {required: true, message: '请上传图片', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        showImg(row) {
            window.open(this.baseUrl+row.path)
        },
        handleAvatarSuccess(res) {
            this.form.path = res.data.filePath
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg'||'image/png'||'image/jpg'
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        openForm() {
            this.formTitle = '添加轮播图'
            this.dialogFormVisible = true
        },
        editForm(row){
            this.formTitle = '修改轮播图'
            this.form.id = row.id
            this.form.name = row.name
            this.form.path = row.path
            this.dialogFormVisible = true
        },
        clearForm() {
            this.form.id = null
            this.form.name = null
            this.form.path = null
            this.$refs.Form.resetFields()
            this.dialogFormVisible = false
        },
        submitForm() {
            this.$refs.Form.validate(async (valid) => {
                if (valid) {
                    if (this.formTitle==='添加轮播图'){
                        let params = this.form
                        let res = await request.addBannerInfo(params)
                        if (res){
                            this.$message.success('添加成功')
                            this.clearForm()
                            await this.getData()
                        }
                    }else{
                        let params = this.form
                        let res = await request.editBannerInfo(params)
                        if (res){
                            this.$message.success('修改成功')
                            this.clearForm()
                            await this.getData()
                        }
                    }
                } else {
                    return false;
                }
            });
        },
        async handleDel(row) {
            let params = [row.id]
            let res = await request.delAllBannerInfo(params)
            if (res){
                this.$message.success('执行成功')
                await this.getData()
            }
        },
        handleDelAll() {
            if (this.multipleSelection.length === 0) {
                this.$message.error('请勾选后批量删除')
                return
            }
            this.$alert('删除后无法恢复，确认删除嘛？', '提示', {
                confirmButtonText: '确定',
                callback: async action => {
                    if (action === 'confirm') {
                        let params = this.multipleSelection.map(item => {
                            return item.id
                        })
                        let res = await request.delAllBannerInfo(params)
                        if (res){
                            this.$message.success('执行成功')
                            await this.getData()
                        }
                    }
                }
            });
        },
        async getData() {
            this.loading = true
            let res = await request.getBannerData()
            if (res){
                this.tableData = res.data.slideList
                this.loading = false
            }
        }
    },
    computed: {
        dataList() {
            let filList = this.tableData
            return filList.filter(p => {
                return p.name.indexOf(this.input) !== -1
            })
        }
    },
    mounted() {
        this.tableHeight = document.querySelector('.box-card').clientHeight - 145
        this.getData()
        console.log("Upload URL:", this.upLoadUrl);  // 打印上传地址
    this.tableHeight = document.querySelector('.box-card').clientHeight - 145;
    this.getData();
    }
}
</script>

<style lang="less">
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

.imgshow {
    height: 80px;
    cursor: pointer;

    img {
        height: 100%;
    }
}

.el-dialog {
    margin-top: -50px;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}

</style>
