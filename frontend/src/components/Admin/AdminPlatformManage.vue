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
            <el-button type="info" class="buttonper" @click="addPlatform()">添加平台</el-button>
            
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="id" label="#" width="300" />
                <el-table-column prop="platformName" label="平台名称" width="550" />
               

                <el-table-column label="操作" v-slot:default="scope" width="450">
                    <el-button type="info" class="buttonper" @click="changePlatformInfor(scope.row.id)">修改信息
                        </el-button>
                    <el-button type="info" class="buttonper" @click="deletePlatform(scope.row.id)">删除平台
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
        <el-dialog v-model="changePlatformInfordialog" title="修改平台信息" width="80%" height="80%" >

            <el-input v-model="this.platformName" placeholder="请输入平台名称">
            </el-input>

            <el-button type="primary" class="btnsin" @click="makesurechange()" >确认</el-button>
        </el-dialog>


        <el-dialog v-model="addPlatformInfordialog" title="添加平台信息" width="80%" height="80%" >

            <el-input v-model="this.newPlatformName" placeholder="请输入平台名称">
            </el-input>

            <el-button type="primary" class="btnsin" @click="makesureadd()" >确认</el-button>
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
                addPlatformInfordialog:false,
                changePlatformInfordialog:false,
                platformName:"",
                newPlatformName:"",
                list:[],
                currentPage:1,
                pageSize:10,
                total:0,
                data:[],
                platformID:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            loadData(){
                this.$axios.post(`/admin/platform/info`)          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    console.log(this.list)
                })
            },
            changePlatformInfor(id){
                this.platformID=id
                var formData = new FormData();
                formData.append('platformID', id);
                this.$axios.post("/admin/platform/name",formData)          //这里填后端端口
                .then(res=>{
                    
                    this.platformName=res.data.message
                })
                this.changePlatformInfordialog=true
            },
            makesurechange(){
                this.$confirm("确定修改吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('platformID', this.platformID);
                    formData.append('platformName', this.platformName);
                    this.$axios.post("/admin/platform/change",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '平台信息修改成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.changePlatformInfordialog=false
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
                    formData.append('platformName', this.newPlatformName);
                    this.$axios.post("/admin/platform/add",formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '平台信息添加成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                        this.newPlatformName=""
                        this.addPlatformInfordialog=false
                        this.loadData()
                    })
                    .catch(err=>{
                        console.log(err.message)
                    })    
                })
            },
            deletePlatform(id){
                this.$confirm("确定删除吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('platformID',id)
                    this.$axios.post('/admin/platform/delete',formData)
                    .then(res=>{
                        if(res.data.code==200){
                            ElMessage({
                                    message: '平台删除成功.',
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
            addPlatform(){
                this.addPlatformInfordialog=true
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