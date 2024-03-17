<template>
    <el-button type="info" class="shopper" @click="tomyshop">我的商店</el-button>  
    <el-button type="info" class="shopper" @click="addProduct">发布商品</el-button>  
</template>

<script>

export default{

    methods:{
        tomyshop(){
            this.$router.push({name:'shophomepage'})
        },
        addProduct(){
            window.open("./addProduct")
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
