<template>
    <div class="title">
            <img src="../../assets/head.jpg" alt="">
            <span class="titlename">在线购物比价系统</span>
        </div> 
    <div class = drop_down>
<el-dropdown class="dropdownplace">
    <span class="el-dropdown-name">
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item>
            <el-button type="info" class="buttonadjust" @click="dialogUserInforVisible = true">个人信息
            </el-button>
        </el-dropdown-item>
        <el-dropdown-item>
            <el-button type="info" class="buttonadjust" @click="quit" >退出
            </el-button>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</div>
  <el-dialog v-model="dialogUserInforVisible" title="你的个人信息" width="80%" >
    <el-form class="userInfor_Form" ref="userInforFormRef" :model="userInforForm" >

        <el-form-item class="inputadjust" prop="userName">
            姓名:
            <el-input v-model="userInforForm.userName" placeholder="请输入真实姓名"
            type="text" maxlength="10" disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="phone">
            手机号：
            <el-input v-model="userInforForm.phone" placeholder="请输入你的大陆手机号"
            onkeyup="value=value.replace(/[^\d]/g,'')" disabled>
                
            </el-input>
        </el-form-item>
            性别：
        <el-form-item class="inputadjust">
            <el-radio-group v-model="userInforForm.sex" class="ml-4" disabled>
                <el-radio label="male" size="large">男</el-radio>
                <el-radio label="female" size="large">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item class="inputadjust" prop="age">
            年龄：
            <el-input v-model="userInforForm.age" placeholder="请输入你的年龄"
            onkeyup="value=value.replace(/[^\d]/g,'')" disabled>
                
            </el-input>
        </el-form-item>

        
</el-form>
    </el-dialog>


</template>

<script>
import { ElMessage } from 'element-plus'
export default{
        data(){
            return{
                userInforForm:{
                    userName:"",
                    phone:"",
                    age:"",
                    sex:"",
                    id:"",
                },
                dialogUserInforVisible:false,
            }
        },
        created(){
            this.loadToken();
        },
        methods:{
            //加载主页数据
            loadToken(){
                var formData = new FormData();
                formData.append('token', localStorage.getItem("token"));
                this.$axios.post('/token',formData)          //这里填后端端口
                .then(res=>{
                    this.userInforForm=res.data.contents
                    console.log(res.data)
                })
                .catch(err=>{
                    console.log(err.message)
                })
            },
            quit(){
                if(localStorage.getItem("token")){
                    localStorage.removeItem("token")
                }
                this.$router.push({name:'login'})
            }
        }
    }

</script>

<style lang="less" scoped>
.drop_down{
    margin-top: 0px;
    height: 60px;

    .dropdownplace{
    height: 60px;
}
    .el-dropdown-name{
    font-size: 18px;
    .el-icon--right{
    margin-top: 15px;
}
}

}

.title{
    display: flex;
    align-items: center;
}
span{
    margin-left: 15px;
    color:gold;
}
img{
    width:60px;
    height:60px;
    border-radius: 50%;
    background-color: #eee;
}

.buttonadjust{
    width:100px;
    height:30px;
    border-color:rgb(72, 0, 255);
    background-color: rgba(0, 34, 255, 0.3);
}
.buttondelete{
    width:70px;
    height:30px;
    background: rgba(255,0,0,.3);
    border-color:red;
    color:black;
}
.buttonalter{
    width:70px;
    height:30px;
    border-color:aqua;
    background-color: rgba(0,255,255,.3);
    color:black;
}
</style>