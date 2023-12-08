package com.example.backend0.controller;

import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.Product;
import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import com.example.backend0.service.PartialProduct;
import com.example.backend0.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProductController
 * @Description
 **/
@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/product")
    public Result getAllProducts(){
        return ResultFactory.buildSuccessResult(productService.getAllProductsWithPartialInfo());
    }
    @PostMapping("/product/search")
    public Result getProductsWhenSearch(@RequestParam("info")String info){
        List<PartialProduct> productList=productService.getAllProductsWithPartialInfo();
        List<PartialProduct> res=new ArrayList<>();
        for(PartialProduct product:productList){
            if(matchSearch(info,product)){
                res.add(product);
            }
        }
        return ResultFactory.buildSuccessResult(res);
    }

    /**
     *     String productName;
     *     String shopName;
     *     String platformName;
     *     String type;
     *     Float currentPrice;
     * @param searchInfo
     * @param product
     * @return
     */
    public boolean matchSearch(String searchInfo,PartialProduct product){
        if(product.getProductName().contains(searchInfo)||product.getType().contains(searchInfo)){
            return true;
        }
        return false;
    }
    public Result upperProduct(@RequestParam("productName")String productName,@RequestParam("type")String type,@RequestParam("productAddress")String productAddress,
                               @RequestParam("productDate")String productDate,@RequestParam("description")String description){
        return upperAProduct(productName,type,productAddress,productDate,description);

    }
    public Result upperAProduct(String productName,String type,String productAddress,
                               String productDate,String description){
        Product product=new Product();
        product.setProductName(productName);
        product.setType(type);
        product.setProductAddress(productAddress);
        product.setProductDate(productDate);
        product.setDescription(description);

        return ResultFactory.buildSuccessResult(productService.save(product));
    }
    public Result upperAConcreteProduct(Integer shopId,Integer platformId,Integer productId,Float currentPrice){
        ConcreteProduct concreteProduct=new ConcreteProduct();
        concreteProduct.setPlatformID(platformId);
        concreteProduct.setProductID(productId);
        concreteProduct.setShopID(shopId);
        concreteProduct.setCurrentPrice(currentPrice);
        return ResultFactory.buildSuccessResult(productService.saveConcreteProduct(concreteProduct));
    }

}
