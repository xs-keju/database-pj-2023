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
            <el-button type="info" class="buttonper" @click="addUser()">添加用户</el-button>
            
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="id" label="#"  width="50px"/>
                <el-table-column prop="userName" label="姓名"  />
                <el-table-column prop="age" label="年龄"  />
                <el-table-column prop="sex" label="性别"  />
                <el-table-column prop="phone" label="手机号"  />

                <el-table-column label="操作" v-slot:default="scope" >
                    <el-button type="info" class="buttonper" @click="changeUserInfor(scope.row.id)">修改信息
                        </el-button>
                    <el-button type="info" class="buttonper" @click="deleteUser(scope.row.id)">删除用户
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

        <el-dialog v-model="changeUserInfordialog" title="修改用户信息" width="80%" height="80%" >
            <el-form class="userInfor_Form" ref="userInforFormRef" :model="userInforForm" >

                <el-form-item class="inputadjust" prop="userName">
                    姓名:
                    <el-input v-model="userInforForm.userName" placeholder="请输入真实姓名"
                    type="text" maxlength="10" >
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="phone">
                    手机号：
                    <el-input v-model="userInforForm.phone" placeholder="请输入你的大陆手机号"
                    onkeyup="value=value.replace(/[^\d]/g,'')" >
                        
                    </el-input>
                </el-form-item>
                    性别：
                <el-form-item class="inputadjust">
                    <el-radio-group v-model="userInforForm.sex" class="ml-4" >
                        <el-radio label="male" size="large">男</el-radio>
                        <el-radio label="female" size="large">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item class="inputadjust" prop="age">
                    年龄：
                    <el-input v-model="userInforForm.age" placeholder="请输入你的年龄"
                    onkeyup="value=value.replace(/[^\d]/g,'')" >
                        
                    </el-input>
                </el-form-item>
                <el-button type="primary" class="btnsin" @click="makesurechange()" >确认</el-button>
            </el-form>
           
        </el-dialog>


        <el-dialog v-model="addUserInfordialog" title="添加用户信息" width="80%" height="80%" >
            <el-form class="userInfor_Form" ref="userInforFormRef" :model="newUserInforForm" >
                <el-form-item class="inputadjust" prop="userName">
                    姓名:
                    <el-input v-model="newUserInforForm.userName" placeholder="请输入真实姓名"
                    type="text" maxlength="10" >
                    </el-input>
                </el-form-item>

                <el-form-item class="inputadjust" prop="phone">
                    手机号：
                    <el-input v-model="newUserInforForm.phone" placeholder="请输入你的大陆手机号"
                    onkeyup="value=value.replace(/[^\d]/g,'')" >
                        
                    </el-input>
                </el-form-item>
                    性别：
                <el-form-item class="inputadjust">
                    <el-radio-group v-model="newUserInforForm.sex" class="ml-4" >
                        <el-radio label="male" size="large">男</el-radio>
                        <el-radio label="female" size="large">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item class="inputadjust" prop="age">
                    年龄：
                    <el-input v-model="newUserInforForm.age" placeholder="请输入你的年龄"
                    onkeyup="value=value.replace(/[^\d]/g,'')" >
                        
                    </el-input>
                </el-form-item>
                <el-form-item class="inputadjust" prop="accountName">
                    账号名:
                <el-input v-model="newUserInforForm.accountName" placeholder="请输入你的账号名"
                type="text"  maxlength="10" >
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="password">
                密码：
                <el-input v-model="newUserInforForm.password" placeholder="请输入你的密码,字母、数字、特殊字符须含至少两种" type="password">
                    
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
                addUserInfordialog:false,
                changeUserInfordialog:false,
                userInforForm:{
                    userName:"",
                    phone:"",
                    age:"",
                    sex:"",
                    id:"",
                },
                newUserInforForm:{
                    userName:"",
                    phone:"",
                    age:"",
                    sex:"",
                    id:"",
                    accountName:"",
                    password:"",
                },
                list:[],
                currentPage:1,
                pageSize:10,
                total:0,
                data:[],
                userID:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            loadData(){
                this.$axios.post(`/admin/user/info`)          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    console.log(this.list)
                })
            },
            changeUserInfor(id){
                this.userID=id
                var formData = new FormData();
                formData.append('userID', id);
                this.$axios.post("/admin/user/concreteInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.userInforForm=res.data.contents
                })
                this.changeUserInfordialog=true
            },
            makesurechange(){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('userID', this.userID);
                    formData.append('userName', this.userInforForm.userName);
                    formData.append('age', this.userInforForm.age);
                    formData.append('sex', this.userInforForm.sex);
                    formData.append('phone', this.userInforForm.phone);
                    this.$axios.post("/admin/user/change",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '用户信息修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.changeUserInfordialog=false
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
                    formData.append('userName', this.newUserInforForm.userName);
                    formData.append('age', this.newUserInforForm.age);
                    formData.append('sex', this.newUserInforForm.sex);
                    formData.append('phone', this.newUserInforForm.phone);
                    formData.append('accountName',this.newUserInforForm.accountName);
                    formData.append('password',this.newUserInforForm.password)
                    formData.append('type', 1);
                    this.$axios.post("/user/register",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '用户信息添加成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.newUserInforForm.userName=""
                        this.newUserInforForm.age=""
                        this.newUserInforForm.sex=""
                        this.newUserInforForm.phone=""
                        this.newUserInforForm.accountName=""
                        this.newUserInforForm.password=""
                        this.addUserInfordialog=false
                        this.loadData()
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })    
                })
            },
            deleteUser(id){
                this.$confirm("确定删除吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('userID',id)
                    this.$axios.post('/admin/user/delete',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '用户删除成功.',
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
            addUser(){
                this.addUserInfordialog=true
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