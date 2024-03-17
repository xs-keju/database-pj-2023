<template>
    <div class="title">
            <img src="../assets/head.jpg" alt="">
            <span class="titlename">在线购物比价系统</span>
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
            this.loadToken();
            this.loadData();
        },
        methods:{
            //加载主页数据
            loadToken(){
                var formData = new FormData();
                formData.append('token', localStorage.getItem("token"));
                this.$axios.post('/token',formData)          //这里填后端端口
                .then(res=>{
                    this.userInforForm=res.data.contents
                    console.log(this.userInforForm)
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
            },
            loadData(){
                this.$axios.post('/test/importFull')          //这里填后端端口
                .then(res=>{
                    console.log(res.data)
                })
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