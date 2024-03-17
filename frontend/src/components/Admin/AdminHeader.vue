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
            <el-button type="info" class="buttonadjust" @click="quit" >退出
            </el-button>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</div>

</template>

<script>
import { ElMessage } from 'element-plus'
export default{
        data(){
            return{
                
            }
        },
        created(){
       
        },
        methods:{
            //加载主页数据
            
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