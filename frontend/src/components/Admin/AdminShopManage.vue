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
            <el-button type="info" class="buttonper" @click="addShop()">添加商家</el-button>
            
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="id" label="#"  />
                <el-table-column prop="shopName" label="商店名"  />
                <el-table-column prop="shopAddress" label="商店地址"  />

                <el-table-column label="操作" v-slot:default="scope" >
                    <el-button type="info" class="buttonper" @click="changeShopInfor(scope.row.id)">修改信息
                        </el-button>
                    <el-button type="info" class="buttonper" @click="deleteShop(scope.row.id)">删除商家
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

        <el-dialog v-model="changeShopInfordialog" title="修改商家信息" width="80%" height="80%" >
            <el-form class="ShopInfor_Form" ref="ShopInforFormRef" :model="shopInforForm" >

                <el-form-item class="inputadjust" prop="shopName">
                    商店名:
                    <el-input v-model="shopInforForm.shopName" placeholder="请输入商店名称"
                    type="text" >
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="shopAddress">
                    商店地址：
                    <el-input v-model="shopInforForm.shopAddress" placeholder="请输入商店地址">
                        
                    </el-input>
                </el-form-item>
                <el-button type="primary" class="btnsin" @click="makesurechange()" >确认</el-button>
            </el-form>
           
        </el-dialog>


        <el-dialog v-model="addShopInfordialog" title="添加商家信息" width="80%" height="80%" >
            <el-form class="ShopInfor_Form" ref="ShopInforFormRef" :model="newShopInforForm" >

                <el-form-item class="inputadjust" prop="shopName">
                    商店名:
                    <el-input v-model="newShopInforForm.shopName" placeholder="请输入商店名称"
                    type="text">
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="shopAddress">
                    商店地址：
                    <el-input v-model="newShopInforForm.shopAddress" placeholder="请输入商店地址">
                        
                    </el-input>
                </el-form-item>
                <el-form-item class="inputadjust" prop="accountName">
                    账号名:
                <el-input v-model="newShopInforForm.accountName" placeholder="请输入你的账号名"
                type="text"  maxlength="10" >
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="password">
                密码：
                <el-input v-model="newShopInforForm.password" placeholder="请输入你的密码,字母、数字、特殊字符须含至少两种" type="password">
                    
                </el-input>
            </el-form-item>
                <el-button type="primary" class="btnsin" @click="makesureadd()" >确认</el-button>
            </el-form>
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
                addShopInfordialog:false,
                changeShopInfordialog:false,
                shopInforForm:{
                    shopName:"",
                    shopAddress:"",
                    id:"",
                },
                newShopInforForm:{
                    shopName:"",
                    shopAddress:"",
                    accountName:"",
                    password:""
                },
                list:[],
                currentPage:1,
                pageSize:10,
                total:0,
                data:[],
                shopID:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            loadData(){
                this.$axios.post(`/admin/shop/info`)          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    console.log(this.list)
                })
            },
            changeShopInfor(id){
                this.shopID=id
                var formData = new FormData();
                formData.append('shopID', id);
                this.$axios.post("/admin/shop/concreteInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.shopInforForm=res.data.contents
                })
                this.changeShopInfordialog=true
            },
            makesurechange(){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('shopID', this.shopID);
                    formData.append('shopName', this.shopInforForm.shopName);
                    formData.append('shopAddress', this.shopInforForm.shopAddress);
                    this.$axios.post("/admin/shop/change",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商家信息修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.changeShopInfordialog=false
                        this.loadData()
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })    
                })
            },
            makesureadd(){
                this.$confirm("确定添加吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('shopName', this.newShopInforForm.shopName);
                    formData.append('shopAddress', this.newShopInforForm.shopAddress);
                    formData.append('accountName',this.newShopInforForm.accountName)
                    formData.append('password',this.newShopInforForm.password)
                    formData.append('type',2)
                    this.$axios.post("/user/register",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商家信息添加成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.newShopInforForm.shopName=""
                        this.newShopInforForm.shopAddress=""
                        this.newShopInforForm.accountName=""
                        this.newShopInforForm.password=""
                        this.addShopInfordialog=false
                        this.loadData()
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })    
                })
            },
            deleteShop(id){
                this.$confirm("确定删除吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('shopID',id)
                    this.$axios.post('/admin/shop/delete',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '商家删除成功.',
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
            addShop(){
                this.addShopInfordialog=true
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