package com.example.backend0.controller;

import com.example.backend0.entity.Platform;
import com.example.backend0.entity.Shop;
import com.example.backend0.entity.User;
import com.example.backend0.repository.*;
import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import com.example.backend0.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Description
 **/
@RestController
public class AdminController {
    @Autowired
    PlatformService platformService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    PlatformRepository platformRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ConcreteProductRepository concreteProductRepository;
    @PostMapping("/admin/platform/add")
    public Result upperPlatform(@RequestParam("platformName")String platformName){
        Platform platform=new Platform();
        platform.setPlatformName(platformName);
        return ResultFactory.buildSuccessResult(platformService.save(platform));
    }
    @PostMapping("/admin/user/concreteInfo")
    public Result user_concreteInfo(@RequestParam("userID")Integer userID){
        return ResultFactory.buildSuccessResult(userRepository.findById(userID));
    }
    @PostMapping("/admin/shop/concreteInfo")
    public Result shop_concreteInfo(@RequestParam("shopID")Integer shopID){
        return ResultFactory.buildSuccessResult(shopRepository.findById(shopID));
    }
    @PostMapping("/admin/user/info")
    public Result user_Info(){
        return ResultFactory.buildSuccessResult(userRepository.findAll());
    }
    @PostMapping("/admin/shop/info")
    public Result shop_Info(){
        return ResultFactory.buildSuccessResult(shopRepository.findAll());
    }
    @PostMapping("/admin/user/delete")
    public Result user_delete(@RequestParam("userID")Integer userID){
        userRepository.deleteById(userID);
        return ResultFactory.buildSuccessResult();
    }
    @PostMapping("/admin/shop/delete")
    public Result shop_delete(@RequestParam("shopID")Integer shopID){
        shopRepository.deleteById(shopID);
        return ResultFactory.buildSuccessResult();
    }
    @PostMapping("/admin/user/change")
    public Result user_change(@RequestParam("userID")Integer userID,@RequestParam(value = "userName") String userName, @RequestParam(value = "age")Integer age, @RequestParam(value = "sex")String sex, @RequestParam(value = "phone")String phone){
        User user=userRepository.findById(userID).orElse(null);
        if(user==null){
            return ResultFactory.buildFailedResult("用户不存在");
        }
        user.setUserName(userName);
        user.setAge(age);
        user.setPhone(phone);
        user.setSex(sex);
        return ResultFactory.buildSuccessResult(userRepository.save(user));
    }
    @PostMapping("/admin/shop/change")
    public Result shop_change(@RequestParam("shopID")Integer shopID,@RequestParam(value = "shopName")String shopName, @RequestParam(value = "shopAddress")String shopAddress){
        Shop shop=shopRepository.findById(shopID).orElse(null);
        if(shop==null){
            return ResultFactory.buildFailedResult("用户不存在");
        }
        shop.setShopName(shopName);
        shop.setShopAddress(shopAddress);
        return ResultFactory.buildSuccessResult(shopRepository.save(shop));
    }
    @PostMapping("/admin/platform/name")
    public Result platform_name(@RequestParam("platformID")Integer platformID){
        Platform platform=platformService.getPlatformById(platformID);
        if(platform==null){
            return ResultFactory.buildFailedResult("平台不存在");
        }
        return ResultFactory.buildSuccessResult(platform.getPlatformName());
    }
    @PostMapping("/admin/platform/info")
    public Result platform_Info(){
        return ResultFactory.buildSuccessResult(platformService.findAll());
    }
    @PostMapping("/admin/platform/change")
    public Result platform_change(@RequestParam("platformID")Integer platformID,@RequestParam(value = "platformName")String platformName){
        Platform platform=platformService.getPlatformById(platformID);
        if(platform==null){
            return ResultFactory.buildFailedResult("用户不存在");
        }
        platform.setPlatformName(platformName);
        return ResultFactory.buildSuccessResult(platformService.save(platform));
    }
    @PostMapping("/admin/platform/delete")
    public Result platform_delete(@RequestParam("platformID")Integer platformID){
        platformRepository.deleteById(platformID);
        return ResultFactory.buildSuccessResult();
    }
    @PostMapping("/admin/product/info")
    public Result product_Info(){
        return ResultFactory.buildSuccessResult(productRepository.findAll());
    }
    @PostMapping("/admin/product/delete")
    public Result product_delete(@RequestParam("concreteProductID")Integer productID){
        concreteProductRepository.deleteById(productID);
        return ResultFactory.buildSuccessResult();
    }
}
