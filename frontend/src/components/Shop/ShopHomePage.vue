<template>
    <el-container class="home-container">
      <el-header class="el-header">
        <Shopheader></Shopheader>
      </el-header>

      <el-container>
        <el-aside class="el-aside" width="200px">
            <Shopaside></Shopaside>
            
        </el-aside>

        <el-main class="el-main">
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="productName" label="商品名称" width="200" />
                <el-table-column prop="type" label="类别" width="100" />
                <el-table-column prop="productAddress" label="产地" />
                <el-table-column prop="productDate" label="生产日期" />
                <el-table-column prop="description" label="商品描述" />
                <el-table-column prop="platformName" label="平台" />
                <el-table-column prop="currentPrice" label="价格" />

                <el-table-column label="操作" v-slot:default="scope" width="400">
                    <el-button type="info" class="buttonper" @click="changeProductInfor(scope.row.concreteProductID)">修改信息
                        </el-button>
                    <el-button type="info" class="buttonper" @click="changePriceInfor(scope.row.concreteProductID)">修改价格
                        </el-button>
                    <el-button type="info" class="buttonper" @click="deleteProduct(scope.row.concreteProductID)">删除商品
                        </el-button>
                    </el-table-column>
            </el-table>
            <div>
                <el-pagination class="pagination"
                
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                
                :page-size="pageSize"
                layout="prev,pager,next,jumper"
                :total="total">
            </el-pagination>
            </div>
        </el-main>

        
        <el-dialog v-model="changeProductInfordialog" title="修改商品信息" width="80%" height="80%" >

            <el-form class="changeProduct_Form" ref="changeProductFormRef" :model="detailProductInfoForm" >

<el-form-item class="inputadjust" prop="productName">
    商品名称:
    <el-input v-model="detailProductInfoForm.productName" placeholder="请输入商品名称"
    type="text">
    </el-input>
</el-form-item>

<el-form-item class="inputadjust" prop="type">
    商品类别：
    <el-input v-model="detailProductInfoForm.type" placeholder="请输入商品类别">
        
    </el-input>
</el-form-item>

<el-form-item class="inputadjust" prop="productAddress">
    产地：
    <el-input v-model="detailProductInfoForm.productAddress" placeholder="请输入商品产地">
        
    </el-input>
</el-form-item>

<el-form-item class="inputadjust" prop="productDate">
    生产日期:
    <el-input v-model="detailProductInfoForm.productDate" placeholder="请输入商品生产日期"
    type="text" >
    </el-input>
</el-form-item>
<el-form-item class="inputadjust" prop="description">
    商品描述：
    <el-input v-model="detailProductInfoForm.description" placeholder="请输入对商品的具体描述">
        
    </el-input>
</el-form-item>
<el-form-item class="inputadjust" prop="platformName">
    出售平台:
    <el-input v-model="detailProductInfoForm.platformName" placeholder="请输入商品的出售平台"
    type="text" disabled >
    </el-input>
</el-form-item>
<el-form-item class="inputadjust" prop="currentPrice">
    单价:
    <el-input v-model="detailProductInfoForm.currentPrice" placeholder="请输入商品单价" disabled>
    </el-input>
</el-form-item>
<el-form-item >
    <el-button type="primary" class="btnsin" @click="makesure(detailProductInfoForm)" >确认</el-button>
    <el-button type="info" class="btnsreset" @click="resetdetailProductInfoForm">重置</el-button>
</el-form-item>
</el-form>
</el-dialog>

<el-dialog v-model="changePriceInfordialog" title="修改商品价格" width="80%" height="80%" >

        修改该商品价格:
        <el-input v-model="this.tochangeprice" placeholder="请输入商品价格"
        type="text">
        </el-input>

        <el-button type="primary" class="btnsin" @click="makesurechangeprice()" >确认</el-button>


</el-dialog>
      </el-container>
    </el-container>
</template>

<script>
import Shopaside from './ShopAside.vue';
import Shopheader from './ShopHeader.vue';
import { ElMessage } from 'element-plus'
    export default{
        components:{
            Shopaside,
            Shopheader
        },
        data(){
            return{
                changeProductInfordialog:false,
                changePriceInfordialog:false,
                tochangeprice:"",
                concreteProductID:"",
                detailProductInfoForm:{
                    productName:"",
                    shopName:"",
                    shopAddress:"",
                    platformName:"",
                    type:"",
                    currentPrice:"",
                    productAddress:"",
                    productDate:"",
                    description:""
                },
                shopID:"",
                list:[],
                currentPage:1,
                pageSize:8,
                total:0,
                data:[],
                userId:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            //加载主页数据
            loadData(){
                this.shopID=localStorage.getItem('shopID')

                this.$axios.post(`/shop/Product?shopID=${this.shopID}`)          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    console.log(this.list)
                })
            },
           
            changeProductInfor(id){
                this.concreteProductID=id
                var formData = new FormData();
                formData.append('concreteProductID', id);
                this.$axios.post("/product/fullInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.detailProductInfoForm=res.data.contents
                })
                this.changeProductInfordialog=true
            },
            changePriceInfor(id){
                this.concreteProductID=id
                var formData = new FormData();
                formData.append('concreteProductID', id);
                this.$axios.post("/product/fullInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.tochangeprice=res.data.contents.currentPrice
                })
                this.changePriceInfordialog=true
            },
            makesurechangeprice(){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                var formData = new FormData();
                formData.append('concreteProductID', this.concreteProductID);
                formData.append('currentPrice',this.tochangeprice)
                this.$axios.post("/product/changePrice",formData)          //这里填后端端口
                .then(res=>{
                    console.log(res.data)
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商品价格修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.changePriceInfordialog=false
                        this.loadData();
                })
            })
            },
            resetdetailProductInfoForm(){
                this.$refs.changeProductFormRef.resetFields();
            },
            makesure(detailProductInfoForm){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('concreteProductID',this.concreteProductID)
                    formData.append('productName', detailProductInfoForm.productName);
                    formData.append('type',detailProductInfoForm.type);
                    formData.append('productAddress',detailProductInfoForm.productAddress);
                    formData.append('productDate', detailProductInfoForm.productDate);
                    formData.append('description', detailProductInfoForm.description);
                    formData.append('currentPrice', detailProductInfoForm.currentPrice);
                    formData.append('platformName', detailProductInfoForm.platformName);
                    formData.append('shopID', this.shopID);
                    this.$axios.post('/shop/changeProduct',formData)
                    .then(res=>{
                        console.log(res.data)
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商品信息修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.changeProductInfordialog=false
                        this.loadData();
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })
                })
            },
            deleteProduct(id){
                this.$confirm("确定删除吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('concreteProductID',id)
                    this.$axios.post('/admin/product/delete',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商品删除成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.loadData();
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })
                })
            },
            handleCurrentChange(val){
                this.currentPage = val;
                this.loadData()
                console.log(this.currentPage)
            }
        }
    }

</script>

<style lang="less" scoped>
.home-container{
    height: 100%;
    background-image: url("../../assets/back.jpg");
    background-repeat: no-repeat;
    background-size: cover;
    background-attachment: fixed;
}
.el-header{
    background-color: #373d41;
    display: flex;
    justify-content: space-between;
    padding-left: 0px;
    padding-right: 0px;
    align-items: center;
    color:#fff;
    font-size: 20px;
}
.exit1{
    border-color:aqua;
    background-color: rgba(0,255,255,.3);
}
.el-aside{
    background-color: rgba(0,0,0,0.3);
}

.el-main{
    color:aqua;
    background-color: rgba(184,134,11,.3);
}
.el-main :deep(.el-table),
.el-main :deep(.el-table__expanded-cell) {
    background-color: transparent;
        color:aquamarine;
}
.el-main :deep(.el-table th),
.el-main :deep(.el-table tr),
.el-main :deep(.el-table td){
        background-color:transparent !important;
        color:aquamarine;
    }
 
.el-dropdown-name{
    top:5px;
}

.el-menu-demo{
    display: flex;
    height: 60px;
}
.buttonper{
        border-color:aqua;
        background-color: rgba(0,255,255,.3);
    }
    .pagination{
        position:absolute;
        left:42%;
        color:aqua;
        bottom:30px;
    }
</style>