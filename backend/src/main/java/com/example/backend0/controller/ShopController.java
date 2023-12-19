package com.example.backend0.controller;

import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.PriceHistory;
import com.example.backend0.entity.Product;
import com.example.backend0.repository.ConcreteProductRepository;
import com.example.backend0.repository.PlatformRepository;
import com.example.backend0.repository.PriceHistoryRepository;
import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * @ClassName ShopController
 * @Description
 **/
@RestController
public class ShopController {
    @Autowired
    ProductController productController;
    @Autowired
    ConcreteProductRepository concreteProductRepository;
    @Autowired
    PriceHistoryRepository priceHistoryRepository;
    @Autowired
    PlatformRepository platformRepository;
    @PostMapping("/shop/addProduct")
    public Result addProduct(@RequestParam("productName")String productName, @RequestParam("type")String type, @RequestParam("productAddress")String productAddress,
                             @RequestParam("productDate")String productDate, @RequestParam("description")String description,
                             @RequestParam("shopID")Integer shopId,@RequestParam("platformID")Integer platformId,@RequestParam("currentPrice")Float currentPrice){
        Result result=productController.upperAProduct(productName,type,productAddress,productDate,description);
        Integer productID=((Product)(result.getContents())).getID();
        return ResultFactory.buildSuccessResult(productController.upperAConcreteProduct(shopId,platformId,productID,currentPrice));
    }
    @PostMapping("/shop/Product")
    public Result getProductByShopId(@RequestParam("shopID")Integer shopID){
        return ResultFactory.buildSuccessResult(concreteProductRepository.getConcreteProductsByShopID(shopID));
    }
    @PostMapping("/shop/changeProduct")
    public Result changeProduct(@RequestParam("productName")String productName, @RequestParam("type")String type, @RequestParam("productAddress")String productAddress,
                                @RequestParam("productDate")String productDate, @RequestParam("description")String description,
                                @RequestParam("shopID")Integer shopId,@RequestParam("platformName")String platformName,@RequestParam("currentPrice")Float currentPrice){
        Result result=productController.upperAProduct(productName,type,productAddress,productDate,description);
        Integer productID=((Product)(result.getContents())).getID();
        Result res=productController.upperAConcreteProduct(shopId, platformRepository.getIdByPlatformName(platformName), productID,currentPrice);
        // 更新历史价格
        PriceHistory priceHistory=new PriceHistory();
        Date sqlDate = new Date(System.currentTimeMillis());
        priceHistory.setPrice(currentPrice);
        priceHistory.setDate(sqlDate);
        priceHistory.setConcreteProductID(((ConcreteProduct) res.getContents()).getID());
        priceHistoryRepository.save(priceHistory);

        return ResultFactory.buildSuccessResult(res.getContents());
    }

}
