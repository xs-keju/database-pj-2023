<template>
    <div class="login_container">
        <div class="login_box">
            <!-- 头像区域 -->
            <div class="avatar_box">
                <img src="../assets/head.jpg" alt="">
            </div>
            <p class="title">
                <el-text class="mx-1" size="large">欢迎登录在线购物比价系统
            </el-text>
        </p>
            <!-- 登录表单区域 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login-form">
        <!-- ID -->
    <el-form-item prop="memberUsername">
    <el-input v-model="loginForm.accountName" placeholder="Please input ID">
      </el-input>
    </el-form-item>
        <!-- PASSWORD -->
    <el-form-item prop="memberPassword">
      <el-input v-model="loginForm.password" type="password" placeholder="Please input password">
    </el-input>
    </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item >
            <el-button type="primary" class="btnsin" @click="login">登录</el-button>
            <el-button type="info" class="btnsreset" @click="resetLoginForm">重置</el-button>
        </el-form-item>

            </el-form>
        <!-- 提示没账号注册 -->
            <p class="register">
                <el-text class="mx-1" size="small">
                    还没有账号？<a href="../register" target="_blank">注册</a>一个！
                </el-text>
             </p>
        </div>
    </div>
</template>


<script>
import { ElMessage } from 'element-plus';
export default{
    data(){
        return{
            accountName:"",
            role:"",
            userInfo:{
                ID:"",
                userName:"",
                age:"",
                sex:"",
                phone:""
            },
            accountInfo:{
                ID:"",
                accountName:"",
                password:"",
                type:"",
                infoID:""
            },

            loginForm:{
                accountName:"",
                password:""
            },
            //验证合法
            loginFormRules:{
                accountName:[
                    {
                        required:true,
                        message:"请输入登录名称",
                        trigger:"blur"
                    },
                    {
                        min:3,
                        max:10,
                        message:"长度在3到10个字符",
                        trigger:"blur"
                    }
                ],
                password:[
                    {
                        required:true,
                        message:"请输入登录密码",
                        trigger:"blur"
                    },
                    {
                        min:6,
                        max:15,
                        message:"长度在6到15个字符",
                        trigger:"blur"
                    }
                ]
            }
        };
    },
    methods:{
        resetLoginForm(){
            // console.log(this)
            this.$refs.loginFormRef.resetFields();
        },

        //验证
        login(){
            this.$refs.loginFormRef.validate((valid)=>{
                if(!valid) return;

                var formData = new FormData();
                formData.append('accountName', this.loginForm.accountName);
                formData.append('password', this.loginForm.password);
                this.$axios.post('/user/login',formData)
                .then(res=>{
                    console.log(res.data)
                    if(res.data.code==200){
                        if(localStorage.getItem('token')){
                            localStorage.removeItem('token');
                        }
                        localStorage.setItem('token', res.data.contents.token)
                        this.role=res.data.contents.type
                        localStorage.setItem('role', res.data.contents.type)
                        this.accountName=res.data.contents.accountName
                        if(this.role==0){
                            ElMessage({
                                message: '登录成功.',
                                type: 'success',
                            })
                            setTimeout(()=>{
                                },2000)
                                this.$router.push({name:'adminhomepage'})
                        }
                        else if(this.role==1){
                            ElMessage({
                                message: '登录成功.',
                                type: 'success',
                            })
                            setTimeout(()=>{
                                },2000)
                                this.$router.push({name:'userhomepage'})
                        }
                        else{
                            setTimeout(()=>{
                                },2000)
                            this.$router.push({name:'shophomepage'})
                            ElMessage({
                                message: '登录成功.',
                                type: 'success',
                            })
                         }
                    }else if(res.data.code==400){
                        ElMessage.error(res.data.message)
                    }
                })
                .catch(err=>{
                    console.log(err.message)
                })
                this.loginForm.accountName="";
                this.loginForm.password="";
            }
        )
    },
    handkeyCode(e) {
            if(e.keyCode === 13){
                this.login();
       }
    },
},

created() {
    window.addEventListener('keydown', this.handkeyCode, true)//开启监听键盘按下事件
},
}
</script>
<style lang="less" scoped>
.login_container{
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

.login_box{
    width:450px;
    height: 300px;
    background: rgba(184,134,11,.3);
    border-radius: 15px;
    position: absolute;
    left:50%;
    top:50%;
    transform: translate(-50%,-50%);
    box-sizing: border-box;
    box-shadow: 15px 15px 15px 25px rgba(184,134,11,.3);

    .register{
        text-align: center;
        position: absolute;
        bottom:0px;
        width:450px;
        .mx-1{
            color:aqua;
        }
        a{
            color:red;
        }
    }
    .title{
        text-align: center;
        position: absolute;
        top:60px;
        left:70px;
        width:300px;
        background-image: linear-gradient(
            to right,
                rgb(3,255,3),
                rgb(5,227,252),
                rgb(244,51,183)
        );
        -webkit-background-clip: text;
        font-weight: bold;
    }
    .mx-1{
        color:transparent;
    }
    }
        @keyframes changeColor {
            100% {
                filter: hue-rotate(360deg);
            }
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
.avatar_box{
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius:50%;
    padding:10px;
    box-shadow: 0 0 10px gold;
    position: absolute;
    left:50%;
    transform: translate(-50%,-50%);
    background: rgba(184,134,11,.3);
    img{
        width:100%;
        height: 100%;
        border-radius: 50%;
        background-color: #eee;
    }
}
.login-form{
    position: absolute;
    bottom:70px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    background-color:transparent;
}
.btnsin{
    border-color:aqua;
    width:60px;
    display: flex;
    justify-content:flex-end;
    position: absolute;
    margin-left: 135px;
    margin-top: 30px;
    background-color: rgba(0,255,255,.3);
}
.btnsreset{
    width:60px;
    display: flex;
    justify-content:flex-end;
    position: absolute;
    margin-left: 215px;
    margin-top:30px;
    background: rgba(255,0,0,.3);
    border-color:red;
}
.registerword{
    position: absolute;
    text-align: sright;
    p{
        text-align: center;
    }
}
.el-form-item__content{
    display: flex;
    justify-content: flex-end;
    position: absolute;
}

// .Userlabel{
//     position: absolute;
//     transform: translate();
// }
</style>
