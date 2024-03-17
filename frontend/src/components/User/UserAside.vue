<template>
    <el-button type="info" class="shopper" @click="toshopmall">商城</el-button>
    <el-button type="info" class="shopper" @click="tomymessage">我的消息</el-button>
    <el-button type="info" class="shopper" @click="tomyfavorite">我的收藏</el-button>
    <el-button type="info" class="shopper" @click="tohotproduct">热点商品</el-button>
</template>

<script>

export default{

    methods:{
        toshopmall(){
            this.$router.push({ name:'userhomepage' })
        },
        
        tomymessage(){
            this.$router.push({name:'usermessage'})
        },
        tomyfavorite(){
            this.$router.push({name:'userfavorite'})
        },
        tohotproduct(){
            this.$router.push({name:'hotproduct'})
        }
    }
}
</script>

<style lang="less" scoped>

.shopper{
    background-color: rgba(0,0,0,0.3);
    width:200px;
    height:80px;
    margin-left: 0;
    border-color:aqua;
    background-color: rgba(0,255,255,.3);
}
</style>
