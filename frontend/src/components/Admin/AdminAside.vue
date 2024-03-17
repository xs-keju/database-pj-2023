<template>
    <el-button type="info" class="admin" @click="toplatforminfor">平台管理</el-button>
    <el-button type="info" class="admin" @click="toshopinfor">商家管理</el-button>
    <el-button type="info" class="admin" @click="touserinfor">用户管理</el-button>
    <el-button type="info" class="admin" @click="toproductinfor">商品管理</el-button>
</template>

<script>

export default{

    methods:{
        toplatforminfor(){
            this.$router.push({ name:'platforminfor' })
        },
        toshopinfor(){
            this.$router.push({ name:'shopinfor' })
        },
        touserinfor(){
            this.$router.push({ name:'userinfor' })
        },
        toproductinfor(){
            this.$router.push({ name:'productinfor' })
        }
        }
    }
</script>

<style lang="less" scoped>

.admin{
    background-color: rgba(0,0,0,0.3);
    width:200px;
    height:80px;
    margin-left: 0;
    border-color:aqua;
    background-color: rgba(0,255,255,.3);
}
</style>
