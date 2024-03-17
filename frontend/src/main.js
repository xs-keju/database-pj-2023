import { createApp } from 'vue'
import './assets/css/global.css'
import ElementPlus from 'element-plus'
import { ElMessage } from 'element-plus';
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import store from './store/index.js'

//添加请求拦截器
axios.interceptors.request.use(
    config =>{
      let token = sessionStorage.getItem('token')
      if (!config.headers.hasOwnProperty('token') && token) {
        config.headers.token = token
      }
      return config
    },
    error =>{
      //对请求错误做什么
      return Promise.reject(error);
    })

//http reponse响应拦截器
axios.interceptors.response.use(
    response =>{
      if (response.data.status === 403) {
        this.$message.error(Response.data.message)
        sessionStorage.removeItem('token')
        this.$router.push('/login')
      } else {
        return response
      }
    },
    error=>{
      if(error.response){
        switch(error.response.status){
          case 401:
            localStorage.removeItem('token');
            router.replace({
              path: '/views/login',
              query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
            })
        }
      }
    })
  

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.config.globalProperties.$axios = axios;
axios.defaults.baseURL = "/api";
axios.defaults.withCredentials = true;
app.use(ElementPlus)
app.use(ElMessage)
app.use(router)
// app.use(store)
app.mount('#app')

