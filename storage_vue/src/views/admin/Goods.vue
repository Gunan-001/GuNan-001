<template>
    <el-card class="box-card">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><a href="/">首页</a></el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">商品模块</a></el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="top-control-group">
            <el-input class="input-with-button" v-model="input" placeholder="请输入内容">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            <el-button type="primary" @click="openForm" style="margin-left: 10px" plain>添加</el-button>
            <el-button type="danger" @click="handleDelAll" style="margin-left: 10px" plain>批量删除</el-button>
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
                prop="commodityName"
                label="商品名称">
            </el-table-column>
            <el-table-column
                prop="commodityImage"
                label="商品图片">
                <template slot-scope="scope">
                    <div class="imgshow">
                        <img :src="baseUrl+scope.row.commodityImage" @click="showImg(scope.row)">
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                prop="commodityInformation"
                label="商品描述">
            </el-table-column>
            <el-table-column
                prop="commodityNumber"
                label="库存数量(kg)">
            </el-table-column>
            <el-table-column
                prop="commodityPrice"
                label="商品单价(元)">
            </el-table-column>
            <el-table-column
                prop="commodityFreight"
                label="商品运费(元)">
            </el-table-column>
            <el-table-column
                prop="commoditTypeName"
                label="商品分类">
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
        <!-- 弹出框 -->
        <el-dialog :show-close="false" :close-on-press-escape="false" :close-on-click-modal="false" top="25px" :title="formTitle" :visible.sync="dialogFormVisible" center width="30%">
            <el-form ref="Form" :model="form" :rules="rules" label-width="100px">
                <el-form-item prop="commodityName" label="商品名称">
                    <el-input v-model="form.commodityName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="commodityPrice" label="商品单价">
                    <el-input v-model.number="form.commodityPrice" autocomplete="off">
                        <template slot="append">元</template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="commodityImage" label="商品图片">
                    <el-upload
                        class="avatar-uploader"
                        :action=upLoadUrl
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="form.commodityImage" :src="baseUrl+form.commodityImage" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item prop="commodityNumber" label="库存数量">
                    <el-input v-model.number="form.commodityNumber" autocomplete="off">
                        <template slot="append">kg</template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="commodityFreight" label="商品运费">
                    <el-input v-model.number="form.commodityFreight" autocomplete="off">
                        <template slot="append">元</template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="commodityClassId" label="商品分类">
                    <el-select v-model="form.commodityClassId" placeholder="请选择" style="width: 100%">
                        <el-option
                            v-for="item in classList"
                            :key="item.id"
                            :label="item.commoditTypeName"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="commodityHot" label="是否推荐">
                    <el-switch
                        v-model="form.commodityHot"
                        active-value=1
                        inactive-value=0>
                    </el-switch>
                </el-form-item>
                <el-form-item prop="commodityInformation" label="商品描述">
                    <el-input
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="请输入内容"
                        v-model="form.commodityInformation">
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
    name: "Goods",
    data() {
        return {
            tableData: [],
            classList: [],
            upLoadUrl: request.upLoadUrl,
            baseUrl:request.baseUrl,
            form: {
                id: null,
                commodityName: null,
                commodityImage: null,
                commodityNumber: null,
                commodityPrice: null,
                commodityFreight: null,
                commodityClassId: null,
                commodityHot: null,
                commodityInformation: null,
            },
            rules: {
                commodityName: [
                    {required: true, message: '请输入商品名称', trigger: 'blur'}
                ],
                commodityImage: [
                    {required: true, message: '请上传商品图片', trigger: 'blur'}
                ],
                commodityNumber: [
                    {required: true, message: '数量不能为空', trigger: 'blur'},
                ],
                commodityPrice: [
                    {required: true, message: '单价不能为空', trigger: 'blur'},
                ],
                commodityFreight: [
                    {required: true, message: '运费不能为空', trigger: 'blur'},
                ],
                commodityClassId: [
                    {required: true, message: '请选择商品类型', trigger: 'change'},
                ],
                commodityHot: [
                    {required: true, message: '请设置是否推荐', trigger: 'change'},
                ],
                commodityInformation: [
                    {required: true, message: '请输入商品描述', trigger: 'blur'},
                ]
            },
            input: '',
            formTitle: '',
            tableHeight: null,
            dialogFormVisible: false,
            multipleSelection: [],
            loading: false,
        }
    },
    methods: {
        handleAvatarSuccess(res) {
            this.form.commodityImage = res.data.filePath
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg'||'image/png'||'image/jpg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG/PNG/JPEG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        showImg(row) {
            window.open(this.baseUrl+row.commodityImage)
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        openForm() {
            this.formTitle = '新增商品信息'
            this.dialogFormVisible = true
        },
        clearForm() {
            this.form.id = null
            this.form.commodityName = null
            this.form.commodityImage = null
            this.form.commodityNumber = null
            this.form.commodityPrice = null
            this.form.commodityFreight = null
            this.form.commodityClassId = null
            this.form.commodityHot = null
            this.form.commodityInformation = null
            this.$refs.Form.resetFields()
            this.dialogFormVisible = false
        },
        editForm(row) {
            this.formTitle = '修改商品信息'
            this.form.id = row.id
            this.form.commodityName = row.commodityName
            this.form.commodityImage = row.commodityImage
            this.form.commodityNumber = row.commodityNumber
            this.form.commodityPrice = row.commodityPrice
            this.form.commodityFreight = row.commodityFreight
            this.form.commodityClassId = row.commodityClassId
            this.form.commodityHot = row.commodityHot
            this.form.commodityInformation = row.commodityInformation
            this.dialogFormVisible = true
        },
        submitForm() {
            this.$refs.Form.validate(async (valid) => {
                if (valid) {
                    if (this.formTitle === '新增商品信息'){
                        let params = this.form
                        let res = await request.addCommodityInfo(params)
                        if (res){
                            this.$message.success('添加成功')
                            this.clearForm()
                            await this.getData()
                        }
                    }else {
                        let params = this.form
                        let res = await request.editCommodityInfo(params)
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
            let param = row.id
            let res = await request.delCommodityInfoById(param)
            if (res) {
                this.$message.success('执行成功')
                await this.getData()
            }
        },
        async handleDelAll() {
            if (this.multipleSelection.length === 0) {
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
                        let res = await request.delAllCommodityInfoById(param)
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
            let res = await request.getCommodityData()
            if (res){
                this.tableData = res.data.commodityList
                this.loading = false
            }
        },
        async getClass() {
            let res = await request.getCommodityClass()
            if (res){
                this.classList = res.data.comodityList
            }
        }
    },
    computed: {
        dataList() {
            let filList = this.tableData
            return filList.filter(p => {
                return p.commodityName.indexOf(this.input) !== -1
            })
        }
    },
    mounted() {
        this.tableHeight = document.querySelector('.box-card').clientHeight - 145
        this.getData()
        this.getClass()
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
