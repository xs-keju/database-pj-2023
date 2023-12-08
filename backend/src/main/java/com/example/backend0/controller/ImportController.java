package com.example.backend0.controller;

import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import com.example.backend0.util.VariableDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ImportController
 * @Description
 **/
@RestController
public class ImportController {
    @Autowired
    AdminController adminController;
    @Autowired
    ProductController productController;
    @Autowired
    UserController userController;
    @PostMapping("/test/importPlatform")
    public void importPlatforms(){
        adminController.upperPlatform("京东");
        adminController.upperPlatform("淘宝");
        adminController.upperPlatform("得物");
        adminController.upperPlatform("拼多多");
    }
    @PostMapping("/test/productImport")
    public void importProducts(){
        // 创建产品1
        productController.upperAProduct("笔记本电脑", "电子产品", "上海市XXX路123号", "2023-01-01", "专业人士的强大笔记本电脑");

        // 创建产品2
        productController.upperAProduct("智能手机", "电子产品", "北京市YYY路456号", "2023-02-15", "具备先进功能的最新款智能手机");

        // 创建产品3
        productController.upperAProduct("咖啡机", "家用电器", "广州市ZZZ路789号", "2023-03-10", "自动咖啡机，让你的早晨更美好");

        // 创建产品4
        productController.upperAProduct("空气净化器", "家用电器", "深圳市AAA路101号", "2023-04-05", "有效清除室内空气污染物");

        // 创建产品5
        productController.upperAProduct("运动鞋", "服装", "南京市BBB路202号", "2023-05-20", "舒适耐穿的运动鞋");
        //
        productController.upperAProduct("平板电脑", "电子产品", "广州市ZZZ路789号", "2023-03-10", "轻薄便携平板电脑，助力高效办公");

        // 创建产品4
        productController.upperAProduct("空调", "家用电器", "深圳市AAA路101号", "2023-04-05", "智能空调，全方位舒适体验");

        // 创建产品5
        productController.upperAProduct("时尚手提包", "时尚配饰", "南京市BBB路202号", "2023-05-20", "潮流设计，展现个性时尚");

        // 创建产品6
        productController.upperAProduct("健康按摩椅", "家居健康", "成都市CCC路303号", "2023-06-15", "舒适按摩椅，缓解疲劳，促进健康");

        // 创建产品7
        productController.upperAProduct("高级化妆品套装", "美妆护肤", "重庆市DDD路404号", "2023-07-10", "精选化妆品，呵护肌肤");

        // 创建产品8
        productController.upperAProduct("电动摩托车", "交通工具", "武汉市EEE路505号", "2023-08-25", "环保电动交通，畅享出行乐趣");

        // 创建产品9
        productController.upperAProduct("书桌椅组合", "家具", "西安市FFF路606号", "2023-09-10", "实用书桌椅，打造舒适学习空间");

        // 创建产品10
        productController.upperAProduct("新鲜水果礼盒", "食品饮料", "天津市GGG路707号", "2023-10-15", "新鲜水果，健康营养，送礼佳品");
    }
    @PostMapping("/test/shopperImport")
    public void importShoppers(){
        userController.register_com("shop1", VariableDefine.getTypeShopper(),"111111","张三",19,"male",null,"复旦好店","邯郸路558");
        userController.register_com("shop2",VariableDefine.getTypeShopper(),"111111","李四",25,"female",null,"同济好店","国权路569");
        userController.register_com("shop3",VariableDefine.getTypeShopper(),"111111","王二",26,"female",null,"上财好店","松花江路路69");
        userController.register_com("shop4",VariableDefine.getTypeShopper(),"111111","李虎",22,"male",null,"交大好店","复兴路589");
    }
    @PostMapping("/test/concreteProductImport")
    public void importConcreteProduct(){
        productController.upperAConcreteProduct(1,1,1, 9.36F);
        productController.upperAConcreteProduct(2,2,2, 90.26F);
        productController.upperAConcreteProduct(1,3,4, 36F);
        productController.upperAConcreteProduct(2,4,3, 98.6F);
        productController.upperAConcreteProduct(3,4,5, 97.6F);
        productController.upperAConcreteProduct(3,4,6, 8.6F);
        productController.upperAConcreteProduct(4,2,7, 9.3F);
        productController.upperAConcreteProduct(4,2,8, 9.53F);
        productController.upperAConcreteProduct(4,2,9, 9.83F);
        productController.upperAConcreteProduct(1,2,10, 955.229F);
    }
    @PostMapping("/test/importFull")
    public Result fullImport(){
        userController.createAdmin();
        importPlatforms();
        importShoppers();
        importProducts();
        importConcreteProduct();
        return ResultFactory.buildSuccessResult();
    }
}
