<template>
    <el-container class="home-container">
      <el-header class="el-header">
        <Adminheader></Adminheader>
      </el-header>

      <el-container>
        <el-aside class="el-aside" width="200px">
            <Adminaside></Adminaside>
            
        </el-aside>
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
        
            }
        },
        created(){
        
        },
        methods:{
           
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