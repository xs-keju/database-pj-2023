package com.example.backend0.controller;

import com.example.backend0.entity.Account;
import com.example.backend0.entity.Shop;
import com.example.backend0.entity.User;
import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import com.example.backend0.service.AccountService;
import com.example.backend0.service.ShopService;
import com.example.backend0.service.UserService;
import com.example.backend0.util.VariableDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description 用户的相关操作
 **/
@RestController
public class UserController {
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;
    @PostMapping("/user/register")
    public Result register_com(@RequestParam("accountName") String accountName, @RequestParam("type")Integer type, @RequestParam("password")String password,
                               @RequestParam(value = "userName",required = false) String userName, @RequestParam(value = "age",required = false)Integer age, @RequestParam(value = "sex",required = false)String sex, @RequestParam(value = "phone",required = false)String phone,
                               @RequestParam(value = "shopName",required = false)String shopName, @RequestParam(value = "shopAddress",required = false)String shopAddress) {
        // 先判断type，只允许user和shopper两种类型进行注册
        if(type==null || ( !type.equals(VariableDefine.getTypeUser()) && !type.equals(VariableDefine.getTypeShopper()) )){
            return ResultFactory.buildFailedResult("用户类型错误");
        }
        // 判断用户名是否重复
        if(accountService.sameAccountName(accountName)){
            return ResultFactory.buildFailedResult("该账号名已被注册");
        }
        // 准备account类
        Account account=new Account();
        account.setAccountName(accountName);
        account.setType(type);
        account.setPassword(password);
        Account account_saved=null;
        // 先进行user注册
        if(type.equals(VariableDefine.getTypeUser())){
            // 进行参数检验
            if(userName==null || age==null || sex==null ||  phone==null){
                return ResultFactory.buildFailedResult("user注册参数缺失");
            }
            if(!(sex.equals("male") || sex.equals("female"))){
                return ResultFactory.buildFailedResult("user的sex属性错误");
            }
            if(!validPhone(phone)){
                return ResultFactory.buildFailedResult("电话号码无效");
            }

            // 存入表格
            User user=new User();
            user.setUserName(userName);
            user.setAge(age);
            user.setPhone(phone);
            user.setSex(sex);
            User user_saved=userService.save(user);
            if(user_saved==null){
                return ResultFactory.buildFailedResult("数据库内部错误-register-user-save");
            }

            account.setInfoID(user_saved.getID());
            account_saved=accountService.save(account);
            if(account_saved==null){
                return ResultFactory.buildFailedResult("数据库内部错误-register-user-account-save");
            }

        }
        else {// 进行商店注册
            // 进行参数检验
            if(shopName==null || shopAddress==null){
                return ResultFactory.buildFailedResult("shop注册参数缺失");
            }
            Shop shop=new Shop();
            shop.setShopName(shopName);
            shop.setShopAddress(shopAddress);
            Shop shop_saved=shopService.save(shop);
            if(shop_saved==null){
                return ResultFactory.buildFailedResult("数据库内部错误-register-shop-save");
            }
            account.setInfoID(shop_saved.getID());
            account_saved=accountService.save(account);
            if(account_saved==null){
                return ResultFactory.buildFailedResult("数据库内部错误-register-shop-account-save");
            }
        }
        return ResultFactory.buildSuccessResult(account_saved);
    }
    public static boolean validPhone(String phone){
        return true;// todo
    }
}
