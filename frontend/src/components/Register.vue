<template>
    <div class="register_container">
        <div class="register_box">
            <div class="headtitle">
                <p class="title">
                    <el-text class="mx-1" size="large">注册你的账号
                </el-text></p>
            </div>
            <div class="chooserole">
                <el-button type="primary" :class="`roleuser ${this.rolesign === 1 ? 'useractive' : ''}`"  @click="toggleRole(1)" >注册用户 </el-button>
                <el-button type="primary" :class="`roleshop ${this.rolesign === 2 ? 'shopactive' : ''}`"  @click="toggleRole(2)" >注册商家 </el-button>
            </div> 
        <el-form class="register_user_Form" ref="registerUserFormRef" :model="registerUserForm" :rules="registerUserFormRules" v-show="this.rolesign==1">

            <el-form-item class="inputadjust" prop="userName">
                姓名:
                <el-input v-model="registerUserForm.userName" placeholder="请输入真实姓名"
                type="text" maxlength="10" >
                </el-input>
            </el-form-item>

            <el-form-item class="inputadjust" prop="phone">
                手机号：
                <el-input v-model="registerUserForm.phone" placeholder="请输入你的大陆手机号"
                onkeyup="value=value.replace(/[^\d]/g,'')">
                    
                </el-input>
            </el-form-item>
                性别：
            <el-form-item class="inputadjust">
                <el-radio-group v-model="registerUserForm.sex" class="ml-4">
                    <el-radio label="male" size="large">男</el-radio>
                    <el-radio label="female" size="large">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item class="inputadjust" prop="age">
                年龄：
                <el-input v-model="registerUserForm.age" placeholder="请输入你的年龄"
                onkeyup="value=value.replace(/[^\d]/g,'')">
                    
                </el-input>
            </el-form-item>

            <el-form-item class="inputadjust" prop="accountName">
                账号名:
                <el-input v-model="registerUserForm.accountName" placeholder="请输入你的账号名"
                type="text"  maxlength="10" >
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="password">
                密码：
                <el-input v-model="registerUserForm.password" placeholder="请输入你的密码,字母、数字、特殊字符须含至少两种" type="password">
                    
                </el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" class="btnsin" @click="usermakesure(registerUserForm)" >确认</el-button>
                <el-button type="info" class="btnsreset" @click="resetuserRegisterForm">重置</el-button>
            </el-form-item>
        </el-form>

        
        <el-form class="register_shop_Form" ref="registerShopFormRef" :model="registerShopForm" :rules="registerShopFormRules" v-show="this.rolesign==2">

            <el-form-item class="inputadjust" prop="shopName">
                商店名:
                <el-input v-model="registerShopForm.shopName" placeholder="请输入商店名"
                type="text" maxlength="64" >
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="shopAddress">
                商店地址:
                <el-input v-model="registerShopForm.shopAddress" placeholder="请输入商店地址"
                type="text"  >
                </el-input>
            </el-form-item>
           
            
            <el-form-item class="inputadjust" prop="accountName">
                账号名:
                <el-input v-model="registerShopForm.accountName" placeholder="请输入你的账号名"
                type="text"  maxlength="10" >
                </el-input>
            </el-form-item>
            <el-form-item class="inputadjust" prop="password">
                密码：
                <el-input v-model="registerShopForm.password" placeholder="请输入你的密码,字母、数字、特殊字符须含至少两种" type="password">
                    
                </el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" class="btnsin" @click="shopmakesure(registerShopForm)" >确认</el-button>
                <el-button type="info" class="btnsreset" @click="resetshopRegisterForm">重置</el-button>
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
            rolesign:1,
            registerUserForm:{
                userName:"",
                phone:"",
                age:"",
                sex:"",
                accountName:"",
                password:"",
            },
            registerShopForm:{
                shopName:"",
                shopAddress:"",
                accountName:"",
                password:"",
            },
            registerUserFormRules:{
                userName:[
                    {
                        required:true,
                        message:"请输入姓名",
                        trigger:"blur"
                    },
                ],
                phone:[
                    {
                        required:true,
                        message:"请输入注册手机号",
                        trigger:"blur"
                    },
                    {
                        min:11,
                        max:11,
                        message:"请保证正确的手机号格式",
                        trigger:"blur"
                    },
                    {
                        validator:checkPhone,
                        trigger:"blur"
                    }
                ],
                age:[
                    {
                        required:true,
                        message:"请输入年龄",
                        trigger:"blur"
                    },
                ],
                sex:[
                    {
                        required:true,
                        message:"请选择性别",
                        trigger:"blur"
                    },
                ],
                accountName:[
                    {
                        required:true,
                        message:"请输入账号名",
                        trigger:"blur"
                    },
                ],
                password:[
                    {
                        required:true,
                        message:"请输入密码",
                        trigger:"blur"
                    },
                    {   
                        min:6,
                        max:32,
                        message:"密码应在6-32字符之间",
                        trigger:"blur"
                    },
                    {
                        validator:checkpassword,
                        trigger:"blur"
                    }
                ],
            },
            registerShopFormRules:{
                shopName:[
                    {
                        required:true,
                        message:"请输入商店名",
                        trigger:"blur"
                    },
                ],
                shopAddress:[
                    {
                        required:true,
                        message:"请输入商店地址",
                        trigger:"blur"
                    },
                ],    
                accountName:[
                    {
                        required:true,
                        message:"请输入账号名",
                        trigger:"blur"
                    },
                ],
                password:[
                    {
                        required:true,
                        message:"请输入密码",
                        trigger:"blur"
                    },
                    {   
                        min:6,
                        max:32,
                        message:"密码应在6-32字符之间",
                        trigger:"blur"
                    },
                    {
                        validator:checkpassword,
                        trigger:"blur"
                    }
                ],
            }
        }
    },
    methods:{
        toggleRole(role) {
            this.rolesign = role;
        },
        resetuserRegisterForm(){ 
            this.$refs.registerUserFormRef.resetFields();
        },
        resetshopRegisterForm(){ 
            this.$refs.registerShopFormRef.resetFields();
        },
        usermakesure(registerUserForm){
            this.$refs.registerUserFormRef.validate((valid)=>{
                if(!valid){
                    ElMessage.error("注册信息不合法");
                    return;
                }
                var formData = new FormData();
                formData.append('userName', this.registerUserForm.userName);
                formData.append('type', 1);
                formData.append('phone', this.registerUserForm.phone);
                formData.append('age', this.registerUserForm.age);
                formData.append('sex', this.registerUserForm.sex);
                formData.append('accountName', this.registerUserForm.accountName);
                formData.append('password', this.registerUserForm.password);
            this.$axios.post('/user/register',formData)
            .then(res=>{
                console.log(res.data)
                if(res.data.code==200){
                    this.$router.push({name:'login'})
                    ElMessage({
                            message: '注册成功.',
                            type: 'success',
                        })
                }else if(res.data.code==400){
                    ElMessage.error(res.data.message)
                }
            })
            .catch(err=>{
                console.log(err.message)
            })
            this.registerUserForm.userName="",
            this.registerUserForm.phone="",
            this.registerUserForm.age="",
            this.registerUserForm.sex="",
            this.registerUserForm.accountName="",
            this.registerUserForm.password=""
        }
        )
        },
        shopmakesure(registerShopForm){
            this.$refs.registerShopFormRef.validate((valid)=>{
                if(!valid){
                    ElMessage.error("注册信息不合法");
                    return;
                }
                var formData = new FormData();
                formData.append('shopName', this.registerShopForm.shopName);
                formData.append('shopAddress', this.registerShopForm.shopAddress);
                formData.append('type', 2);
                formData.append('accountName', this.registerShopForm.accountName);
                formData.append('password', this.registerShopForm.password);
            this.$axios.post('/user/register',formData)
            .then(res=>{
                console.log(res.data)
                if(res.data.code==200){
                    this.$router.push({name:'login'})
                    ElMessage({
                            message: '注册成功.',
                            type: 'success',
                        })
                }else if(res.data.code==400){
                    ElMessage.error(res.data.message)
                }
                
                // if(res.data==0){
                //     this.$router.push({name:'login'})
                //     ElMessage({
                //             message: '注册成功.',
                //             type: 'success',
                //         })
                //     }
                // else if(res.data==1)
                //     ElMessage.error("用户名已被注册")
                // else if(res.data==2)
                //     ElMessage.error("手机号已被注册")
                // else if(res.data==3)
                //     ElMessage.error("身份证号已被注册")
                // else
                //     ElMessage.error("邮箱已被注册")
            })
            .catch(err=>{
                console.log(err.message)
            })
            this.registerShopForm.shopName="",
            this.registerShopForm.shopAddress="",
            this.registerShopForm.accountName="",
            this.registerShopForm.password=""
        }
        )
        },
}
}

const checkPhone =(registerFormRule,value,callback)=>{
    if(!value){
        return callback(new Error("请输入手机号"))
    }
    else if(!phoneVerify(value)){
        callback(new Error("不是正确的手机号格式"))
    } else {
        callback()
    }
}
function phoneVerify(phone){
		var reg = /^((13\d)|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0,1,3,5,7,8])|(18[0-9])|(19[8,9]))\d{8}/;
		if (reg.test(phone)) {
			return  true;
		}else{
			return false;
		}
}

function passwordLevel(password) {
    var Modes = 0;
   
    for (var i = 0; i < password.length; i++) {
        Modes |= CharMode(password.charCodeAt(i));
    }  
    return bitTotal(Modes);
 //CharMode函数

function CharMode(iN) {
    if (iN >= 48 && iN <= 57)//数字
        return 1;
    if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90))
 //大小写
        return 2;
    else
        return 4; //特殊字符
 }
 //bitTotal函数
 function bitTotal(num) {
    var modes = 0;
   
    for (var i = 0; i < 3; i++) {
        if (num & 1) 
            modes++;
        num >>>= 1;
    }
    return modes;
 }
}

const checkpassword = (registerFormRule,value,callback) => {
  if (!value) {
    return callback(new Error('请输入密码'))
  }
  else if (passwordLevel(value)<=1) {
        callback(new Error('未包含两种类型'))
      } else {
        callback()
      }
}
</script>

<style lang="less" scoped>
.register_container{
  // background-color: #2b4b6b;
    height: 100%;
    width: 100%;
    background: url("../assets/back.jpg") no-repeat center center fixed;
    background-size: cover;
    position:absolute;
    left:0;
    top:0;
    // z-index:-1;
}
.register_box{
    width:400px;
    height:500px;
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
.register_user_Form{
    position: absolute;
    bottom:-20px;
    width:100%;
    padding:0 20px;
    box-sizing: border-box;
    color:rgba(0,255,255,0.8);
}
.register_shop_Form{
    position: absolute;
    bottom:100px;
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
