<template>
    <div class="addProduct_container">
        <div class="addProduct_box">
            <div class="headtitle">
                <p class="title">
                    <el-text class="mx-1" size="large">发布你的商品
                </el-text></p>
            </div>

        <el-form class="addProduct_Form" ref="addProductFormRef" :model="addProductForm" :rules="addProductFormRules">

            <el-form-item class="inputadjust" prop="productName">
                商品名称:
                <el-input v-model="addProductForm.productName" placeholder="请输入商品名称"
                type="text">
                </el-input>
            </el-form-item>

            <el-form-item class="inputadjust" prop="type">
                商品类别：
                <el-input v-model="addProductForm.type" placeholder="请输入商品类别">
                    
                </el-input>
            </el-form-item>

            <el-form-item class="inputadjust" prop="productAddress">
                产地：
                <el-input v-model="addProductForm.productAddress" placeholder="请输入商品产地">
                    
                </el-input>
            </el-form-item>

            <el-form-item class="inputadjust" prop="productDate">
                生产日期:
                <el-input v-model="addProductForm.productDate" placeholder="请输入商品生产日期"
                type="text" >
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="description">
                商品描述：
                <el-input v-model="addProductForm.description" placeholder="请输入对商品的具体描述">
                    
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="platformID" label="平台:">
                <el-select v-model="addProductForm.platformID" class="m-2" placeholder="选择你要出售的平台" >
                <el-option
                v-for="item in this.platformInfo"
                :key="item.platformName"
                :label="item.platformName"
                :value="item.id"
                />
            </el-select>
            </el-form-item>
            <el-form-item class="inputadjust" prop="currentPrice">
                单价:
                <el-input v-model="addProductForm.currentPrice" placeholder="请输入商品单价">
                </el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" class="btnsin" @click="makesure(addProductForm)" >确认</el-button>
                <el-button type="info" class="btnsreset" @click="resetaddProductForm">重置</el-button>
            </el-form-item>
        </el-form>

        </div>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus';
export default{
    data(){
        return {
            shopID:"",
            addProductForm:{
                productName:"",
                type:"",
                productAddress:"",
                productDate:"",
                description:"",
                platformID:"",
                currentPrice:"",
            },
            platformInfo:[],
            addProductFormRules:{
                productName:[
                    {
                        required:true,
                        message:"请输入商品名",
                        trigger:"blur"
                    },
                ],
                type:[
                    {
                        required:true,
                        message:"请输入注册手机号",
                        trigger:"blur"
                    },
                ],
                productAddress:[
                    {
                        required:true,
                        message:"请输入商品产地",
                        trigger:"blur"
                    },
                ],
                productDate:[
                    {
                        required:true,
                        message:"请输入生产日期",
                        trigger:"blur"
                    },
                ],
                description:[
                    {
                        required:true,
                        message:"请输入具体描述",
                        trigger:"blur"
                    },
                ],
                platformName:[
                    {
                        required:true,
                        message:"请输入选择的平台",
                        trigger:"blur"
                    },
                    
                ],
                currentPrice:[
                    {
                        required:true,
                        message:"请输入单价",
                        trigger:"blur"
                    },
                ]
            },
            
        }
    },
    created(){
        this.loadPlatformInfo();
        this.loadshopID()
    },
    methods:{
        loadPlatformInfo(){
            this.$axios.post('/admin/platform/info')
            .then(res=>{
                this.platformInfo=res.data.contents
                console.log(res.data.contents)
            })
           
        },
        loadshopID(){
            var formDatatoken = new FormData();
                formDatatoken.append('token', localStorage.getItem("token"));
                this.$axios.post('/token',formDatatoken)          //这里填后端端口
                .then(res=>{
                    this.shopID=res.data.contents.id
                    console.log(res.data.contents)
                })
        },
        resetaddProductForm(){ 
            this.$refs.addProductFormRef.resetFields();
        },
        makesure(addProductForm){
            
            this.$refs.addProductFormRef.validate((valid)=>{
                if(!valid){
                    ElMessage.error("商品信息不合法");
                    return;
                }

                var formData = new FormData();
                formData.append('productName', this.addProductForm.productName);
                formData.append('type',this.addProductForm.type);
                formData.append('productAddress', this.addProductForm.productAddress);
                formData.append('productDate', this.addProductForm.productDate);
                formData.append('description', this.addProductForm.description);
                formData.append('currentPrice', this.addProductForm.currentPrice);
                formData.append('platformID', this.addProductForm.platformID);
                formData.append('shopID', this.shopID);
            this.$axios.post('/shop/addProduct',formData)
            .then(res=>{
                console.log(res.data.contents)
                if(res.data.code==200){
                    ElMessage({
                            message: '商品发布成功.',
                            type: 'success',
                        })
                }else if(res.data.code==400){
                    ElMessage.error(res.data.message)
                }
            })
            .catch(err=>{
                console.log(err.message)
            })

          
            this.addProductForm.productName="",
            this.addProductForm.type="",
            this.addProductForm.productAddress="",
            this.addProductForm.productDate="",
            this.addProductForm.description="",
            this.addProductForm.platformName="",
            this.addProductForm.currentPrice=""
        }
        )
        },
}
}

</script>

<style lang="less" scoped>
.addProduct_container{
  // background-color: #2b4b6b;
    height: 100%;
    width: 100%;
    background: url("../../assets/back.jpg") no-repeat center center fixed;
    background-size: cover;
    position:absolute;
    left:0;
    top:0;
    // z-index:-1;
}
.addProduct_box{
    width:400px;
    height:550px;
    // background-color: #fff;
    background: rgba(184,134,11,.3);
    border-radius: 15px;
    box-sizing: border-box;
    box-shadow: 15px 15px 15px 25px rgba(184,134,11,.3);
    position: relative;
    left:50%;
    top:50%;
    transform: translate(-50%,-50%);
    .mx-1{
        color:gold;
        font-weight:bold;
        font-size:medium;
    }
    .roleuser{
        height:50px;
        width:200px;
        position:absolute;
        border-color:aqua;
        background-color: rgba(0,255,255,.3);
    }
    .useractive{
        height:50px;
        width:200px;
        position:absolute;
        border-color:aqua;
        background-color: rgba(255,0,255,.3);
    }
    .roleshop{
        height:50px;
        width:200px;
        position:absolute;
        border-color:aqua;
        background-color: rgba(0,255,255,.3);
        top: 0px;
        left: 200px
    }
    .shopactive{
        height:50px;
        width:200px;
        position:absolute;
        border-color:aqua;
        background-color: rgba(255,0,255,.3);
        top: 0px;
        left: 200px
    }
}
.headtitle{
    position: absolute;
    width:100px;
    left:150px;
    top:-70px;
    .title{
        text-align: center;
    }
}
.addProduct_Form{
    position: absolute;
    bottom:20px;
    width:100%;
    padding:0 20px;
    box-sizing: border-box;
    color:rgba(0,255,255,0.8);
}

.btnsin{
    width:60px;
    display: flex;
    justify-content:flex-end;
    position: absolute;
    margin-left: 110px;
    margin-top: 40px;
    border-color:aqua;
    background-color: rgba(0,255,255,.3);
}
.btnsreset{
    width:60px;
    display: flex;
    justify-content:flex-end;
    position: absolute;
    margin-left: 190px;
    margin-top:40px;
    background: rgba(255,0,0,.3);
    border-color:red;
}
.inputadjust{
    margin-bottom: 10px;
}
.el-input{

// :deep(.el-input__inner){
// background-color: transparent;
// border-color: transparent;
// box-shadow:2px 2px 0 0, rgba(0,0,0,.2);
// caret-color:rgba(0,255,255,.8);
:deep(.el-input__wrapper) {
background-color: transparent !important;
}
:deep(.el-input__inner){
    background-color: transparent;
    caret-color:rgba(0,255,255,.8);
    color:aqua;
}
}
</style>
