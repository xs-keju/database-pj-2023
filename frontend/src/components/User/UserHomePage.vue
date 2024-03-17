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
            
            <el-input v-model="this.keyword" placeholder="输入要查询的商品名称"
                type="text" ></el-input>
            <el-button type="info" class="buttonper" @click="search(this.keyword)">搜索</el-button>
            <el-button type="info" class="buttonper" @click="checkallcommodity">商品列表</el-button>
            <el-table :data="this.list" style="width: 100% margin:auto">
                <el-table-column prop="productName" label="商品名称" width="200" />
                <el-table-column prop="shopName" label="所属商家" width="100" />
                <el-table-column prop="platformName" label="所属平台" />
                <el-table-column prop="type" label="商品类别" />
                <el-table-column prop="currentPrice" label="价格" />
                <el-table-column label="操作" v-slot:default="scope" width="400">
                    <el-button type="info" class="buttonper" @click="checkdetail(scope.row.id)">详细信息
                        </el-button>
                    <el-button type="info" class="buttonper" @click="choosefavorite(scope.row.id)">收藏
                        </el-button>
                    <el-button type="info" class="buttonper" @click="compare(scope.row.id)">变化趋势比较
                        </el-button>
                    <el-button type="info" class="buttonper" @click="comparePrice(scope.row.id)">比价
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
  
        <el-dialog v-model="checkdetaildialog" title="商品详细信息" width="80%" height="80%" >
            <el-button type="info"  @click="this.checkstatus=1">查看详细信息
                        </el-button>
                    <el-button type="info"  @click="checkPriceHistoryWeek">近一周历史价格
                        </el-button>
                    <el-button type="info"  @click="checkPriceHistoryMonth">近一月历史价格
                        </el-button>
                    <el-button type="info"  @click="checkPriceHistoryYear">近一年历史价格
                        </el-button>
    <el-form class="detailProductInfo_Form" ref="detailProductInfoFormRef" :model="detailProductInfoForm" v-show="this.checkstatus==1" >

        <el-form-item class="inputadjust" prop="productName">
            商品名称:
            <el-input v-model="detailProductInfoForm.productName" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="shopName">
            所属商家:
            <el-input v-model="detailProductInfoForm.shopName" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="shopAddress">
            所属商家地址:
            <el-input v-model="detailProductInfoForm.shopAddress" type="text"  disabled>
            </el-input>
        </el-form-item>
        
        
        <el-form-item class="inputadjust" prop="platformName">
            所属平台:
            <el-input v-model="detailProductInfoForm.platformName" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="type">
            商品类别:
            <el-input v-model="detailProductInfoForm.type" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="currentPrice">
            当前价格:
            <el-input v-model="detailProductInfoForm.currentPrice" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="productAddress">
            产地:
            <el-input v-model="detailProductInfoForm.productAddress" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="productDate">
            生产日期:
            <el-input v-model="detailProductInfoForm.productDate" type="text"  disabled>
            </el-input>
        </el-form-item>

        <el-form-item class="inputadjust" prop="description">
            商品描述:
            <el-input v-model="detailProductInfoForm.description" type="text"  disabled>
            </el-input>
        </el-form-item>
    </el-form>
    <div v-if="this.checkstatus==2">
        最低价格：{{this.minpriceweek}} 日期：{{this.mindateweek}}
        <div ref="myChartWeek" style="width: 100%; height: 400px;"></div>
    <el-table :data="this.pricehistoryweekReverse" style="width: 100%" >
		<el-table-column type="index" label="#" width="100" align="center" header-align="center">
			</el-table-column>
                <el-table-column prop="price" label="价格"  align="center" 
                header-align="center">
				</el-table-column>
                <el-table-column prop="date" label="日期"  align="center" 
                header-align="center">
				</el-table-column>

            </el-table>
        </div>

        <div v-if="this.checkstatus==3">
        最低价格：{{this.minpricemonth}} 日期：{{this.mindatemonth}}
        <div ref="myChartMonth" style="width: 100%; height: 400px;"></div>
    <el-table :data="this.pricehistorymonthReverse" style="width: 100%" >
		<el-table-column type="index" label="#" width="100" align="center" header-align="center">
			</el-table-column>
                <el-table-column prop="price" label="价格"  align="center" 
                header-align="center">
				</el-table-column>
                <el-table-column prop="date" label="日期"  align="center" 
                header-align="center">
				</el-table-column>

            </el-table>
        </div>

        <div v-if="this.checkstatus==4">
        最低价格：{{this.minpriceyear}} 日期：{{this.mindateyear}}
        <div ref="myChartYear" style="width: 100%; height: 400px;"></div>
    <el-table :data="this.pricehistoryyearReverse" style="width: 100%" >
        <el-table-column type="index" label="#" width="100" align="center" header-align="center">
			</el-table-column>
                <el-table-column prop="price" label="价格"  align="center" 
                header-align="center">
				</el-table-column>
                <el-table-column prop="date" label="日期"  align="center" 
                header-align="center">
				</el-table-column>
            </el-table> 
        </div>
    </el-dialog>

    <el-dialog v-model="this.favoritedialog" title="设置价格下限" width="80%" >
                <div>当前价格：{{this.tempprice}}  </div>
                <div>设置你的价格下限：
                    <div class="btnandinput">
							<div style="width: 50px;">
                                <el-input v-model="this.minimumPriceset"></el-input></div>
							</div>
                </div>
                            <el-button type="info" class="buttonadjust" @click="donothing">取消</el-button>
                <el-button type="info" class="buttonadjust" @click="favorite()">确认
                 </el-button>
            </el-dialog>

            <el-dialog v-model="this.comparedialog" title="商品价格变化趋势" width="90%" height="90%">
          <div>
            <el-button type="info" class="buttonadjust" @click="weekcompare()">周对比
                 </el-button>
            <el-button type="info" class="buttonadjust" @click="monthcompare()">月对比
                 </el-button>
            <div>价格差最大商家：{{ this.maxPriceDifferenceShopName }}   价格差最大值：{{ this.maxPriceDifference}}   价格差最小商家：{{this.minPriceDifferenceShopName}}   价格差最小值：{{ this.minPriceDifference}}</div>
        <div ref="myChartCompare" style="width: 100%; height: 400px;"></div>
       

        </div>
            </el-dialog>

        <el-dialog v-model="this.comparepricedialog" title="商品比价" width="90%" height="90%">
          <div>

            <div>价格最小商家与平台:{{ this.mincompareName }} 价格最小值：{{ this.mincomparePrice }}</div> 
        <div ref="myChartComparePrice" style="width: 100%; height: 400px;"></div>
       

        </div>

    </el-dialog>
      </el-container>
    </el-container>
</template>

<script>
import * as echarts from 'echarts';
import Useraside from './UserAside.vue';
import Userheader from './UserHeader.vue'
import { ElMessage } from 'element-plus';
    export default{
        
        components:{
            Useraside,
            Userheader,
        },
        data(){
            return{
                myChartWeek:null,
                myChartMonth:null,
                myChartYear:null,
                myChartCompare:null,
                myChartComparePrice:null,
                list:[],
                productDetailInfo:[],
                pricehistoryweek:[],
                pricehistoryweekReverse:[],
                pricehistoryweekTime:[],
                pricehistoryweekPrice:[],
                pricehistorymonth:[],
                pricehistorymonthReverse:[],
                pricehistorymonthTime:[],
                pricehistorymonthPrice:[],
                pricehistoryyear:[],
                pricehistoryyearReverse:[],
                pricehistoryyearTime:[],
                pricehistoryyearPrice:[],
                detailProductInfoForm:{
                    productName:"",
                    shopName:"",
                    shopAddress:"",
                    platformName:"",
                    type:"",
                    currentPrice:"",
                    productAddress:"",
                    productDate:"",
                    description:""
                },
                minpriceweek:"",
                mindateweek:"",
                minpricemonth:"",
                mindatemonth:"",
                minpriceyear:"",
                mindateyear:"",
                minimumPriceset:"",
                keyword:"",
                checkstatus:1,
                checkdetaildialog:false,
                favoritedialog:false,
                comparedialog:false,
                comparepricedialog:false,
                currentPage:1,
                pageSize:10,
                total:0,
                data:[],
                userId:"",
                tempID:"",
                compareinfo:[],
                searchsign:0,
                maxPriceDifference:"",
                maxPriceDifferenceShopName:"",
                minPriceDifference:"",
                minPriceDifferenceShopName:"",
                tempcompareid:"",
                priceCurrentCompareName:[],
                priceCurrentComparePrice:[],
                priceCurrentInfo:[],
                mincomparePrice:"",
                mincompareName:"",
            }
        },
        created(){
            this.loadData();
        },
        methods:{
            //加载主页数据
            loadData(){
                this.$axios.get('/product')          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    console.log(this.data)
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    
                })

                this.userId=localStorage.getItem('userID')
            },
            search(keyword){
                this.$axios.post(`/product/search?info=${keyword}`)
                .then(res=>{
                    console.log(res.data)
                    this.data=res.data.contents
                    
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                    
                })
                this.searchsign=1
            },
            checkallcommodity(){
                this.$axios.get('/product')          //这里填后端端口
                .then(res=>{
                    this.data=res.data.contents
                    
                    this.list=this.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
                    this.total=this.data.length
                 })
                 this.searchsign=0
            },
            checkdetail(id){
                var formData = new FormData();
                formData.append('concreteProductID', id);
                this.$axios.post("/product/fullInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.detailProductInfoForm=res.data.contents
                    console.log(res.data.contents)
                })
                this.checkdetaildialog=true
                this.tempID=id
            },
            checkPriceHistoryWeek(){
                this.checkstatus=2
                var formData = new FormData();
                formData.append('concreteProductID', this.tempID);
                formData.append('dataRange', "week");
                this.$axios.post("/product/priceInfo",formData)         //这里填后端端口
                .then(res=>{
                    this.pricehistoryweek=res.data.contents
                    console.log(res.data.contents)
                    const copiedData=this.pricehistoryweek.slice()
                    this.pricehistoryweekReverse=reverseArray(this.pricehistoryweek)
                    this.pricehistoryweek=copiedData
                    let priceTimeList=this.pricehistoryweek
                    let minPrice = priceTimeList[0].price;
                    let minTime = priceTimeList[0].date;
                    // 遍历列表，找到最小价格和对应时间
                    for (let i = 1; i < priceTimeList.length; i++) {
                        const { price, date } = priceTimeList[i];
                        if (price < minPrice) {
                            minPrice = price;
                            minTime = date;
                        }
                    }
                    this.minpriceweek=minPrice //注意修改这里
                    this.mindateweek=minTime
                    console.log(res.data.contents)
                    this.pricehistoryweekTime=this.pricehistoryweek.map(item=>item.date)
                    this.pricehistoryweekPrice=this.pricehistoryweek.map(item=>item.price)
                     // 在对话框打开后，初始化图表实例并绑定到 $refs.chartContainer
                    this.myChartWeek = echarts.init(this.$refs.myChartWeek, 'light');
                    const options = {
                        legend: {	//图表上方的图例
                            data: ['近一周价格历史']
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.pricehistoryweekTime
                        },
                        yAxis: {
                            type: 'value',
                            name: '价格',   // y轴名称
                            nameTextStyle: {	// y轴名称样式
                                fontWeight: 600,
                                fontSize: 18
	                        }
                        },
                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        series: [{
                            name: '近一周价格历史',
                            type: 'line',
                            data: this.pricehistoryweekPrice
                        }]
                    };

                    this.myChartWeek.setOption(options);
                })
            },

            checkPriceHistoryMonth(){
                this.checkstatus=3
                var formData = new FormData();
                formData.append('concreteProductID', this.tempID);
                formData.append('dataRange', "month");
                this.$axios.post("/product/priceInfo",formData)         //这里填后端端口
                .then(res=>{
                    this.pricehistorymonth=res.data.contents
                    const copiedData=this.pricehistorymonth.slice()
                    this.pricehistorymonthReverse=reverseArray(this.pricehistorymonth)
                    this.pricehistorymonth=copiedData
                    let priceTimeList=this.pricehistorymonth
                    let minPrice = priceTimeList[0].price;
                    let minTime = priceTimeList[0].date;
                    // 遍历列表，找到最小价格和对应时间
                    for (let i = 1; i < priceTimeList.length; i++) {
                        const { price, date } = priceTimeList[i];
                        if (price < minPrice) {
                            minPrice = price;
                            minTime = date;
                        }
                    }
                    this.minpricemonth=minPrice //注意修改这里
                    this.mindatemonth=minTime
                    console.log(res.data.contents)
                    this.pricehistorymonthTime=this.pricehistorymonth.map(item=>item.date)
                    this.pricehistorymonthPrice=this.pricehistorymonth.map(item=>item.price)
                     // 在对话框打开后，初始化图表实例并绑定到 $refs.chartContainer
                    this.myChartMonth = echarts.init(this.$refs.myChartMonth, 'light');
                    const options = {
                        legend: {	//图表上方的图例
                            data: ['近一月价格历史']
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.pricehistorymonthTime
                        },
                        yAxis: {
                            type: 'value',
                            name: '价格',   // y轴名称
                            nameTextStyle: {	// y轴名称样式
                                fontWeight: 600,
                                fontSize: 18
	                        }
                        },
                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        series: [{
                            name: '近一月价格历史',
                            type: 'line',
                            data: this.pricehistorymonthPrice
                        }]
                    };

                    this.myChartMonth.setOption(options);
                })
            },

            checkPriceHistoryYear(){
                this.checkstatus=4
                var formData = new FormData();
                formData.append('concreteProductID', this.tempID);
                formData.append('dataRange', "year");
                this.$axios.post("/product/priceInfo",formData)         //这里填后端端口
                .then(res=>{
                    this.pricehistoryyear=res.data.contents
                    const copiedData=this.pricehistoryyear.slice()
                    this.pricehistoryyearReverse=reverseArray(this.pricehistoryyear)
                    this.pricehistoryyear=copiedData
                    let priceTimeList=this.pricehistoryyear
                    let minPrice = priceTimeList[0].price;
                    let minTime = priceTimeList[0].date;
                    // 遍历列表，找到最小价格和对应时间
                    for (let i = 1; i < priceTimeList.length; i++) {
                        const { price, date } = priceTimeList[i];
                        if (price < minPrice) {
                            minPrice = price;
                            minTime = date;
                        }
                    }
                    this.minpriceyear=minPrice //注意修改这里
                    this.mindateyear=minTime
                    console.log(res.data.contents)
                    this.pricehistoryyearTime=this.pricehistoryyear.map(item=>item.date)
                    this.pricehistoryyearPrice=this.pricehistoryyear.map(item=>item.price)
                     // 在对话框打开后，初始化图表实例并绑定到 $refs.chartContainer
                    this.myChartYear = echarts.init(this.$refs.myChartYear, 'light');
                    const options = {
                        legend: {	//图表上方的图例
                            data: ['近一年价格历史']
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.pricehistoryyearTime
                        },
                        yAxis: {
                            type: 'value',
                            name: '价格',   // y轴名称
                            nameTextStyle: {	// y轴名称样式
                                fontWeight: 600,
                                fontSize: 18
	                        }
                        },
                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        series: [{
                            name: '近一年价格历史',
                            type: 'line',
                            data: this.pricehistoryyearPrice
                        }]
                    };

                    this.myChartYear.setOption(options);
                })
            },
            favorite(){
                this.$confirm("确定收藏吗?", "提示", {
                })
                .then(()=>{
                    var formData = new FormData();
                    formData.append('concreteProductID', this.tempID);
                    formData.append('token', localStorage.getItem('token'));
                    formData.append('minimumPrice',this.minimumPriceset)
                    this.$axios.post("/user/favorite",formData)         //这里填后端端口
                    .then(res=>{
                        console.log(res.data.contents)
                        if(res.data.code==200){
                            ElMessage({
                                    message: '收藏成功.',
                                    type: 'success',
                                })
                        }else if(res.data.code==400){
                            ElMessage.error(res.data.message)
                        }
                    })
                    this.favoritedialog=false
                    this.minimumPriceset=""
                })
            },
            choosefavorite(id){
                var formData = new FormData();
                formData.append('concreteProductID', id);
                this.$axios.post("/product/fullInfo",formData)          //这里填后端端口
                .then(res=>{
                    this.tempprice=res.data.contents.currentPrice
                })
                this.tempID=id
                setTimeout(() => {
                // 在延迟后打开对话框的代码
                // 例如，可以使用浏览器原生的对话框函数，如alert、confirm等
                this.favoritedialog = true;
            }, 500); // 500毫秒，即0.5秒
            },
            compare(id){
                this.tempcompareid=id
                this.myChartCompare=null
                this.comparedialog = true;
                var formData = new FormData();
                formData.append('concreteProductID',id);
                this.$axios.post("/product/compare/month",formData)          //这里填后端端口
                .then(res=>{
                    
                    console.log(res.data.contents)
                    this.maxPriceDifference=res.data.contents.maxPriceDifference
                    this.maxPriceDifferenceShopName=res.data.contents.maxPriceDifferenceShopName+' '+res.data.contents.maxPriceDifferencePlatformName
                    this.minPriceDifference=res.data.contents.minPriceDifference
                    this.minPriceDifferenceShopName=res.data.contents.minPriceDifferenceShopName+' '+res.data.contents.minPriceDifferencePlatformName
                    this.compareinfo=res.data.contents.compareDTOS
                    console.log( this.compareinfo)
                    this.compareinfopricehistoryyearTime=this.compareinfo.map(item=>item.priceHistory.map(entry=>entry.date))
                    console.log(this.compareinfopricehistoryyearTime)
                    this.compareinfopricehistoryyearPrice=this.compareinfo.map(item=>item.priceHistory.map(entry=>entry.price))
                    this.compareinfoshopName=this.compareinfo.map(item=>item.shopName+' '+item.platformName)
                    this.compareinfoplatformName=this.compareinfo.map(item=>item.platformName)
                    // 遍历数据列表
                   
               
                this.myChartCompare = echarts.init(this.$refs.myChartCompare, 'light');
                    const options = {
                        legend: {	//图表上方的图例
                            data: this.compareinfoshopName
                        },
                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.compareinfopricehistoryyearTime[0]
                        },
                        yAxis: {
                            type: 'value',
                            name: '价格',   // y轴名称
                            nameTextStyle: {	// y轴名称样式
                                fontWeight: 600,
                                fontSize: 18
	                        }
                        },
                        
                        series:this.compareinfopricehistoryyearPrice.map((prices,index)=>({
                            name:this.compareinfoshopName[index],
                            type:'line',
                            data:prices
                        }))
                    };
                   
                    this.myChartCompare.setOption(options);
                })
                setTimeout(() => {
                // 在延迟后打开对话框的代码
                // 例如，可以使用浏览器原生的对话框函数，如alert、confirm等
                this.comparedialog = true;
            }, 500); // 500毫秒，即0.5秒
            },
            comparePrice(id){
                this.comparepricedialog=true
                var formData = new FormData();
                formData.append('concreteProductID', id);
                this.$axios.post("/product/compare",formData)          //这里填后端端口
                .then(res=>{
                    this.priceCurrentInfo=res.data.contents
                    this.priceCurrentCompareName=this.priceCurrentInfo.map(item=>item.shopName)
                    this.priceCurrentComparePrice=this.priceCurrentInfo.map(item=>item.price)
                    let priceTimeList=this.priceCurrentInfo
                    let minPrice = priceTimeList[0].price;
                    let minTime = priceTimeList[0].shopName+' '+priceTimeList[0].platformName;
                    // 遍历列表，找到最小价格和对应时间
                    for (let i = 1; i < priceTimeList.length; i++) {
                        const { price, shopName ,platformName } = priceTimeList[i];
                        if (price < minPrice) {
                            minPrice = price;
                            minTime = shopName+' '+platformName;
                        }
                    }
                    this.mincomparePrice=minPrice //注意修改这里
                    this.mincompareName=minTime
                    this.myChartComparePrice = echarts.init(this.$refs.myChartComparePrice, 'light');
                    const options = {

                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.priceCurrentCompareName
                        },
                        yAxis: {
                           
                        },
                        
                        series:[{
                            name:'数据',
                            type:'bar',
                            data:this.priceCurrentComparePrice
                        }]
                    };
                    this.myChartComparePrice.setOption(options);
                }
                )
            },
            monthcompare(){
                this.myChartCompare=null
                this.comparedialog = true;
                var formData = new FormData();
                formData.append('concreteProductID', this.tempcompareid);
                this.$axios.post("/product/compare/month",formData)          //这里填后端端口
                .then(res=>{
                    
                    console.log(res.data.contents)
                    this.maxPriceDifference=res.data.contents.maxPriceDifference
                    this.maxPriceDifferenceShopName=res.data.contents.maxPriceDifferenceShopName+' '+res.data.contents.maxPriceDifferencePlatformName
                    this.minPriceDifference=res.data.contents.minPriceDifference
                    this.minPriceDifferenceShopName=res.data.contents.minPriceDifferenceShopName+' '+res.data.contents.minPriceDifferencePlatformName
                    this.compareinfo=res.data.contents.compareDTOS
                    console.log( this.compareinfo)
                    this.compareinfopricehistoryyearTime=this.compareinfo.map(item=>item.priceHistory.map(entry=>entry.date))
                    console.log(this.compareinfopricehistoryyearTime)
                    this.compareinfopricehistoryyearPrice=this.compareinfo.map(item=>item.priceHistory.map(entry=>entry.price))
                    this.compareinfoshopName=this.compareinfo.map(item=>item.shopName+' '+item.platformName)
                    this.compareinfoplatformName=this.compareinfo.map(item=>item.platformName)
                    // 遍历数据列表
                   
               
                this.myChartCompare = echarts.init(this.$refs.myChartCompare, 'light');
                    const options = {
                        legend: {	//图表上方的图例
                            data: this.compareinfoshopName
                        },
                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.compareinfopricehistoryyearTime[0]
                        },
                        yAxis: {
                            type: 'value',
                            name: '价格',   // y轴名称
                            nameTextStyle: {	// y轴名称样式
                                fontWeight: 600,
                                fontSize: 18
	                        }
                        },
                        
                        series:this.compareinfopricehistoryyearPrice.map((prices,index)=>({
                            name:this.compareinfoshopName[index],
                            type:'line',
                            data:prices
                        }))
                    };
                    
                    this.myChartCompare.setOption(options);
                })
                setTimeout(() => {
                // 在延迟后打开对话框的代码
                // 例如，可以使用浏览器原生的对话框函数，如alert、confirm等
                this.comparedialog = true;
            }, 500); // 500毫秒，即0.5秒
            },
            weekcompare(){
                this.myChartCompare=null
                this.comparedialog = true;
                var formData = new FormData();
                formData.append('concreteProductID', this.tempcompareid);
                this.$axios.post("/product/compare/week",formData)          //这里填后端端口
                .then(res=>{
                    
                    console.log(res.data.contents)
                    this.maxPriceDifference=res.data.contents.maxPriceDifference
                    this.maxPriceDifferenceShopName=res.data.contents.maxPriceDifferenceShopName+' '+res.data.contents.maxPriceDifferencePlatformName
                    this.minPriceDifference=res.data.contents.minPriceDifference
                    this.minPriceDifferenceShopName=res.data.contents.minPriceDifferenceShopName+' '+res.data.contents.minPriceDifferencePlatformName
                    this.compareinfo=res.data.contents.compareDTOS
                    console.log( this.compareinfo)
                    this.compareinfopricehistoryyearTime=this.compareinfo.map(item=>item.priceHistory.map(entry=>entry.date))
                    console.log(this.compareinfopricehistoryyearTime)
                    this.compareinfopricehistoryyearPrice=this.compareinfo.map(item=>item.priceHistory.map(entry=>entry.price))
                    this.compareinfoshopName=this.compareinfo.map(item=>item.shopName+item.platformName)
                    this.compareinfoplatformName=this.compareinfo.map(item=>item.platformName)
                    // 遍历数据列表
                   
               
                this.myChartCompare = echarts.init(this.$refs.myChartCompare, 'light');
                    const options = {
                        legend: {	//图表上方的图例
                            data: this.compareinfoshopName
                        },
                        tooltip: {   //鼠标放到图上的数据展示样式
                            trigger: 'axis'
                        },
                        xAxis: {
                            type: 'category',
                            name: '日期',   // x轴名称
                            nameTextStyle: {	// x轴名称样式
                                fontWeight: 600,
                                fontSize: 18
                            },
                            data: this.compareinfopricehistoryyearTime[0]
                        },
                        yAxis: {
                            type: 'value',
                            name: '价格',   // y轴名称
                            nameTextStyle: {	// y轴名称样式
                                fontWeight: 600,
                                fontSize: 18
	                        }
                        },
                        
                        series:this.compareinfopricehistoryyearPrice.map((prices,index)=>({
                            name:this.compareinfoshopName[index],
                            type:'line',
                            data:prices
                        }))
                    };
                    
                    this.myChartCompare.setOption(options);
                })
                setTimeout(() => {
                // 在延迟后打开对话框的代码
                // 例如，可以使用浏览器原生的对话框函数，如alert、confirm等
                this.comparedialog = true;
            }, 500); // 500毫秒，即0.5秒
            },
            donothing(){
                this.favoritedialog=false
                this.minimumPrice=""
            },
            handleCurrentChange(val){
                this.currentPage = val;
                if(this.searchsign==0){
                    this.loadData()
                }else if(this.searchsign==1){
                    this.search(this.keyword)
                }
            },
        },
       
    }
function reverseArray(array) {
    return array.reverse();
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