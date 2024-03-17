import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import AdminHomePage from '../components/Admin/AdminHomePage.vue'
import UserHomePage from '../components/User/UserHomePage.vue'
import ShopHomePage from '../components/Shop/ShopHomePage.vue'
import Testaddinfo from '../components/testaddinfo.vue'
import AddProduct from '../components/Shop/addProduct.vue'
import PlatformInfor from '../components/Admin/AdminPlatformManage.vue'
import UserInfor from '../components/Admin/AdminUserManage.vue'
import ShopInfor from '../components/Admin/AdminShopManage.vue'
import ProductInfor from '../components/Admin/AdminProductManage.vue'
import UserMessage from '../components/User/UserMessage.vue'
import UserFavorite from '../components/User/UserFavorite.vue'
import TestPic from '../components/User/testpic.vue'
import HotProduct from '../components/User/HotProduct.vue'
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
      {
        path:'/',
        redirect:'/login'
      },
      {
        path:'/login',
        name:"login",
        component:Login
      },
      {
        path:'/register',
        name:"register",
        component:Register
      },
      {
        path:'/admin/homepage',
        name:"adminhomepage",
        component:AdminHomePage
      },
      {
        path:'/user/homepage',
        name:"userhomepage",
        component:UserHomePage
      },
      {
        path:'/user/message',
        name:"usermessage",
        component:UserMessage
      },
      {
        path:'/shop/homepage',
        name:"shophomepage",
        component:ShopHomePage
      },
      {
        path:'/testaddinfo',
        name:"testaddinfo",
        component:Testaddinfo
      },
      {
        path:'/shop/addProduct',
        name:"addProduct",
        component:AddProduct
      },
      {
        path:'/admin/platformManage',
        name:"platforminfor",
        component:PlatformInfor
      },
      {
        path:'/admin/userManage',
        name:"userinfor",
        component:UserInfor
      },
      {
        path:'/admin/shopManage',
        name:"shopinfor",
        component:ShopInfor
      },
      {
        path:'/admin/productManage',
        name:"productinfor",
        component:ProductInfor
      },
      {
        path:'/user/favorite',
        name:"userfavorite",
        component:UserFavorite
      },
      {
        path:'/testpic',
        name:"testpic",
        component:TestPic
      }, 
      {
        path:'/user/hotproduct',
        name:"hotproduct",
        component:HotProduct
      },
    ]
})

router.beforeEach((to, from, next) => {
  // 如果是登录页则放行
    if (to.path.startsWith('/login')||to.path.startsWith('/register')||to.path.startsWith('/testaddinfo')) return next()
    // 查看token是否存在
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')
    if (!token) {
      ElMessage.error('检测到您还未登录,请登录后操作！')
      return next('/login')
    }
    if (to.path.startsWith('/admin/')&& role != 0 && !from.path.startsWith('/login')){
      ElMessage.error('检测到您无权限访问该页面,请重新登录后操作！')
      return next('/login')
    }
    if(to.path.startsWith('/shop/')&& (role != 2) &&(!from.path.startsWith('/login'))){
      ElMessage.error('检测到您无权限访问该页面,请重新登录后操作！')
      return next('/login')
    }
    else if (to.path.startsWith('/user/')&& (role != 1) && (!from.path.startsWith('/login')) ){
      ElMessage.error('检测到您无权限访问该页面,请重新登录后操作！')
      return next('/login')
    }
    else
      next()
  })

export default router