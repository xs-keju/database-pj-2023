<template>
    <el-container class="home-container">
      <el-header class="el-header">
        <Adminheader></Adminheader>
      </el-header>

      <el-container>
        <el-aside class="el-aside" width="200px">
            <Adminaside></Adminaside>
        </el-aside>
        <el-main class="el-main">
            <el-button type="info" class="buttonper" @click="addProduct()">添加商品</el-button>
            
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="productName" label="商品名称" width="150" />
                <el-table-column prop="type" label="类别" width="100" />
                <el-table-column prop="productAddress" label="产地" />
                <el-table-column prop="productDate" label="生产日期" />
                <el-table-column prop="description" label="商品描述" />
                <el-table-column prop="platformName" label="平台" />
                <el-table-column prop="currentPrice" label="价格" />

                <el-table-column label="操作" v-slot:default="scope" width="450">
                    <el-button type="info" class="buttonper" @click="changeProductInfor(scope.row.concreteProductID)">修改信息
                        </el-button>
                    <el-button type="info" class="buttonper" @click="changePriceInfor(scope.row.concreteProductID)">修改价格
                        </el-button>
                    <el-button type="info" class="buttonper" @click="changePriceHistoryInfor(scope.row.concreteProductID)">修改价格历史表
             
                        </el-button>
                    <el-button type="info" class="buttonper" @click="deleteProduct(scope.row.concreteProductID)">删除
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
            <el-form class="addProduct_Form" ref="newProductInforFormRef" :model="productInforForm">
                <el-form-item class="inputadjust" prop="productName">
                        商品名称:
                        <el-input v-model="productInforForm.productName" type="text"  >
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="shopName">
                        所属商家:
                        <el-input v-model="productInforForm.shopName" type="text" disabled >
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="shopAddress">
                        所属商家地址:
                        <el-input v-model="productInforForm.shopAddress" type="text" >
                        </el-input>
                    </el-form-item>
                    
                    
                    <el-form-item class="inputadjust" prop="platformName">
                        所属平台:
                        <el-input v-model="productInforForm.platformName" type="text"  disabled>
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="type">
                        商品类别:
                        <el-input v-model="productInforForm.type" type="text"  >
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="currentPrice">
                        当前价格:
                        <el-input v-model="productInforForm.currentPrice" type="text" >
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="productAddress">
                        产地:
                        <el-input v-model="productInforForm.productAddress" type="text" >
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="productDate">
                        生产日期:
                        <el-input v-model="productInforForm.productDate" type="text" >
                        </el-input>
                    </el-form-item>

                    <el-form-item class="inputadjust" prop="description">
                        商品描述:
                        <el-input v-model="productInforForm.description" type="text" >
                        </el-input>
                    </el-form-item>
                </el-form>
                <el-button type="primary" class="btnsin" @click="makesurechange()" >确认</el-button>
        </el-dialog>


        <el-dialog v-model="addProductInfordialog" title="添加商品信息" width="80%" height="80%" >
            <el-form class="addProduct_Form" ref="newProductInforFormRef" :model="newProductInforForm">

                <el-form-item class="inputadjust" prop="productName">
                    商品名称:
                    <el-input v-model="newProductInforForm.productName" placeholder="请输入商品名称"
                    type="text">
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="type">
                    商品类别：
                    <el-input v-model="newProductInforForm.type" placeholder="请输入商品类别">
                        
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="productAddress">
                    产地：
                    <el-input v-model="newProductInforForm.productAddress" placeholder="请输入商品产地">
                        
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="productDate">
                    生产日期:
                    <el-input v-model="newProductInforForm.productDate" placeholder="请输入商品生产日期"
                    type="text" >
                    </el-input>
                </el-form-item>
                <el-form-item class="inputadjust" prop="description">
                    商品描述：
                    <el-input v-model="newProductInforForm.description" placeholder="请输入对商品的具体描述">
                        
                    </el-input>
                </el-form-item>
                <el-form-item class="inputadjust" prop="shopID" label="商家:">
                    <el-select v-model="newProductInforForm.shopID" class="m-2" placeholder="选择你要出售的商家" >
                    <el-option
                    v-for="item in this.shopInfo"
                    :key="item.shopName"
                    :label="item.shopName"
                    :value="item.id"
                    />
                </el-select>
                </el-form-item>
                <el-form-item class="inputadjust" prop="platformID" label="平台:">
                    <el-select v-model="newProductInforForm.platformID" class="m-2" placeholder="选择你要出售的平台" >
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
                    <el-input v-model="newProductInforForm.currentPrice" placeholder="请输入商品单价">
                    </el-input>
                </el-form-item>
                <el-form-item >
                    <el-button type="primary" class="btnsin" @click="makesureadd()" >确认</el-button>

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
        <el-dialog v-model="changePriceHistoryInfordialog" title="修改历史价格表" width="80%" height="80%" >
            <el-button type="info"  @click="addHistoryInfor()">添加历史价格记录</el-button>
            
            <el-table :data="this.priceHistoryList" style="width: 100% margin:auto">
                <el-table-column prop="date" label="修改日期"  />
                <el-table-column prop="price" label="价格"  />


                <el-table-column label="操作" v-slot:default="scope" >
                    <el-button type="info"  @click="changeHistoryInfor(scope.row.id)">修改记录
                        </el-button>
                    <el-button type="info"  @click="deleteHistoryInfor(scope.row.id)">删除记录
                        </el-button>

                    </el-table-column>
            </el-table>
           
        </el-dialog>
        <el-dialog v-model="changePriceHistoryInfordetaildialog" title="修改记录" width="80%" height="80%" >
            <el-form class=" priceHistory_Form" ref=" priceHistoryFormRef" :model=" priceHistoryForm">

        <el-form-item class="inputadjust" prop="date">
            日期:
            <el-input v-model=" priceHistoryForm.date" placeholder="请输入日期"
            type="text">
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="price">
            价格：
            <el-input v-model=" priceHistoryForm.price" placeholder="请输入价格">
                
            </el-input>
        </el-form-item>


            </el-form>
          
            <el-button type="primary" class="btnsin" @click="changeHistoryInformakesure()" >确认</el-button>
        </el-dialog>

        <el-dialog v-model="addPriceHistoryInfordialog" title="新增记录" width="80%" height="80%" >
            <el-form class=" addpriceHistory_Form" ref=" addpriceHistoryFormRef" :model=" addpriceHistoryForm">

        <el-form-item class="inputadjust" prop="date">
            日期:
            <el-input v-model=" addpriceHistoryForm.date" placeholder="请输入日期"
            type="text">
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="price">
            价格：
            <el-input v-model=" addpriceHistoryForm.price" placeholder="请输入价格">
                
            </el-input>
        </el-form-item>


            </el-form>
          
            <el-button type="primary" class="btnsin" @click="addHistoryInformakesure()" >确认</el-button>
        </el-dialog>
    </el-container>
</el-container>

</template>

<script>
import { ElMessage } from 'element-plus'
import Adminheader from './AdminHeader.vue'
import Adminaside from './AdminAside.vue'
export default{
        components:{
            Adminheader,
            Adminaside
        },
        data(){
            return{
                addProductInfordialog:false,
                changeProductInfordialog:false,
                changePriceInfordialog:false,
                changePriceHistoryInfordialog:false,
                changePriceHistoryInfordetaildialog:false,
                addPriceHistoryInfordialog:false,
                tochangeprice:"",
                priceHistoryForm:{
                    price:"",
                    date:"",

                },
                addpriceHistoryForm:{
                    price:"",
                    date:"",

                },
                productInforForm:{
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
                newProductInforForm:{
                    productName:"",
                    type:"",
                    productAddress:"",
                    productDate:"",
                    description:"",
                    platformID:"",
                    shopID:"",
                    currentPrice:"",
                },
                list:[],
                priceHistoryList:[],
                currentPage:1,
                pageSize:8,
                total:0,
                currentPage1:1,
                pageSize1:8,
                total1:0,
                data:[],
                priceHistroyData:[],
                concreteProductID:"",
                platformInfo:[],
                shopInfo:[],
                tempPriceHistoryID:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            loadData(){
                this.$axios.post(`/admin/product/info`)          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    console.log(this.list)
                })

                this.$axios.post('/admin/platform/info')
                .then(res=>{
                    this.platformInfo=res.data.contents
                    console.log(res.data.contents)
                })

                this.$axios.post('/admin/shop/info')
                .then(res=>{
                    this.shopInfo=res.data.contents
                    console.log(res.data.contents)
                })
            },
            changeProductInfor(id){
                this.concreteProductID=id
                var formData = new FormData();
                formData.append('concreteProductID', id);
                this.$axios.post("/product/fullInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.productInforForm=res.data.contents
                    console.log(res.data.contents)
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
            makesurechange(){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('concreteProductID',this.concreteProductID)
                    formData.append('productName', this.productInforForm.productName);
                    formData.append('type',this.productInforForm.type);
                    formData.append('productAddress', this.productInforForm.productAddress);
                    formData.append('productDate', this.productInforForm.productDate);
                    formData.append('description', this.productInforForm.description);
                    formData.append('currentPrice', this.productInforForm.currentPrice);
                    formData.append('platformName', this.productInforForm.platformName);

                    this.$axios.post("/admin/product/change",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商品信息修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.changeProductInfordialog=false
                        this.loadData()
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })    
                })
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
            makesureadd(){
                this.$confirm("确定添加吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('productName', this.newProductInforForm.productName);
                    formData.append('type',this.newProductInforForm.type);
                    formData.append('productAddress', this.newProductInforForm.productAddress);
                    formData.append('productDate', this.newProductInforForm.productDate);
                    formData.append('description', this.newProductInforForm.description);
                    formData.append('currentPrice', this.newProductInforForm.currentPrice);
                    formData.append('platformID', this.newProductInforForm.platformID);
                    formData.append('shopID', this.newProductInforForm.shopID);
                    this.$axios.post('/shop/addProduct',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商品信息添加成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.newProductInforForm.productName="",
                        this.newProductInforForm.type="",
                        this.newProductInforForm.productAddress="",
                        this.newProductInforForm.productDate="",
                        this.newProductInforForm.description="",
                        this.newProductInforForm.platformName="",
                        this.newProductInforForm.currentPrice=""
                        this.newProductInforForm.ProductName=""
                        
                        this.addProductInfordialog=false
                        this.loadData()
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
                        this.loadData()
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })
                })
            },
            addProduct(){
                this.addProductInfordialog=true
            },
            addHistoryInfor(){
                this.addPriceHistoryInfordialog=true
            },
            addHistoryInformakesure(){
                this.$confirm("确定提交吗?", "提示", {
                })
                .then(()=>{
                var formData = new FormData();
                formData.append('currentPrice',this.addpriceHistoryForm.price)
                formData.append('Date',this.addpriceHistoryForm.date)
                formData.append('concreteProductID',this.concreteProductID)
                this.$axios.post('/product/changePrice',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '记录新增成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                    })
                })
                this.changePriceHistoryInfordialog=false
            },
            changePriceHistoryInfor(id){

                this.concreteProductID=id
                var formData = new FormData();
                formData.append('concreteProductID',id)
                this.$axios.post('/admin/priceHistory/getByConcreteProductId',formData)
                    .then(res=>{
                        this.priceHistoryList=res.data.contents
                    })
                this.changePriceHistoryInfordialog=true

            },
            changeHistoryInfor(id){

                this.tempPriceHistoryID=id
                
                var formData = new FormData();
                formData.append('priceHistoryID',this.tempPriceHistoryID)
 
                this.$axios.post('/priceHistory/getBypriceHistoryID',formData)
                .then(res=>{
                        this.priceHistoryForm.price=res.data.contents.price
                        this.priceHistoryForm.date=res.data.contents.date
                    })
                this.changePriceHistoryInfordetaildialog=true
            },
            changeHistoryInformakesure(){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                var formData = new FormData();
                formData.append('priceHistoryId',this.tempPriceHistoryID)
                formData.append('price',this.priceHistoryForm.price)
                formData.append('date',this.priceHistoryForm.date)
                formData.append('concreteProductID',this.concreteProductID)
                this.$axios.post('/priceHistory/change',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '记录修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                    })
                    this.changePriceHistoryInfordetaildialog=false
                    this.changePriceHistoryInfordialog=false
                })

            },
            deleteHistoryInfor(id){
                this.$confirm("确定删除吗?", "提示", {
                })
                .then(()=>{
                var formData = new FormData();
                formData.append('priceHistoryID',id)
                this.$axios.post('/admin/priceHistory/delete',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '记录删除成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                    })
                    this.changePriceHistoryInfordialog=false
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
.btnandinput{
    display:flex;
    flex-direction: row;
    justify-content: flex-start;
}
</style>