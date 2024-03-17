<template>
    <el-container class="home-container">
      <el-header class="el-header">
        <Userheader></Userheader>
      </el-header>

      <el-container>
        <el-aside class="el-aside" width="200px">
            <Useraside></Useraside>
            
        </el-aside>

        <el-main class="el-main">
           价格降低提醒：
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="productName" label="商品名称" width="200" />
                <el-table-column prop="shopName" label="所属商家" width="250" />
                <el-table-column prop="platformName" label="所属平台" />
                <el-table-column prop="currentPrice" label="当前价格" />
                <el-table-column prop="minimumPrice" label="期望价格" />
                <el-table-column prop="time" label="时间" />
                <el-table-column label="操作" v-slot:default="scope" width="250">
                   
                    
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
       

      </el-container>
    </el-container>
</template>

<script>
import Useraside from './UserAside.vue';
import Userheader from './UserHeader.vue'

    export default{
        components:{
            Useraside,
            Userheader
        },
        data(){
            return{
                list:[],
                currentPage:1,
                pageSize:10,
                total:0,
                data:[],
                userId:"",
                tempID:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            //加载主页数据
            loadData(){
                var formData = new FormData();
                formData.append('token', localStorage.getItem("token"));

                this.$axios.post('/usr/message',formData)          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    console.log(res.data.contents)
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    console.log(this.list)
                })

                this.userId=localStorage.getItem('userID')
            },
            
            handleCurrentChange(val){
                this.currentPage = val;
                this.loadData()
            },
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