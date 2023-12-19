package com.example.backend0.controller;

import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.Product;
import com.example.backend0.repository.ConcreteProductRepository;
import com.example.backend0.repository.ProductRepository;
import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import com.example.backend0.dto.PartialProductDTO;
import com.example.backend0.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
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
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ConcreteProductRepository concreteProductRepository;
    @GetMapping("/product")
    public Result getAllProducts(){
        return ResultFactory.buildSuccessResult(productService.getAllProductsWithPartialInfo());
    }
    @PostMapping("/product/search")
    public Result getProductsWhenSearch(@RequestParam("info")String info){
        List<PartialProductDTO> productList=productService.getAllProductsWithPartialInfo();
        List<PartialProductDTO> res=new ArrayList<>();
        for(PartialProductDTO product:productList){
            if(matchSearch(info,product)){
                res.add(product);
            }
        }
        return ResultFactory.buildSuccessResult(res);
    }
    @PostMapping("/product/fullInfo")
    public Result getFullProductsInfoByConcreteProductId(@RequestParam("concreteProductID")Integer concreteProductId){
        if(concreteProductId!=null)
            return ResultFactory.buildSuccessResult(productService.findFullProductInfoByConcreteProductID((concreteProductId)));
        else
            return ResultFactory.buildFailedResult("参数为空");
    }
    @PostMapping("/product/priceInfo")
    public Result getPriceInfo(@RequestParam("concreteProductID")Integer concreteProductID,@RequestParam("dataRange")String dataRange){
        if(concreteProductID==null||dataRange==null){
            return ResultFactory.buildFailedResult("参数缺失");
        }
        Date sqlDate = new Date(System.currentTimeMillis());
        if(dataRange.equals("year")) {
            return ResultFactory.buildSuccessResult(productService.getPriceHistoryByYear(concreteProductID,sqlDate));
        }else if(dataRange.equals("month")){
            return ResultFactory.buildSuccessResult(productService.getPriceHistoryByMonth(concreteProductID,sqlDate));
        } else if (dataRange.equals("week")) {
            return ResultFactory.buildSuccessResult(productService.getPriceHistoryByWeek(concreteProductID,sqlDate));
        }else {
            return ResultFactory.buildFailedResult("dataRange错误");
        }
    }

    public boolean matchSearch(String searchInfo, PartialProductDTO product){
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
        Product product=productRepository.findByAllInfo(productName,type,productAddress,productDate,description);
        if(product==null) {
            System.out.println("new product!");
            product = new Product();
        }
        product.setProductName(productName);
        product.setType(type);
        product.setProductAddress(productAddress);
        product.setProductDate(productDate);
        product.setDescription(description);
        return ResultFactory.buildSuccessResult(productService.save(product));
    }
    public Result upperAConcreteProduct(Integer shopId,Integer platformId,Integer productId,Float currentPrice){

        ConcreteProduct concreteProduct=concreteProductRepository.findByAllInfo(shopId,platformId,productId,currentPrice);
        if(concreteProduct==null){
            concreteProduct=new ConcreteProduct();
            System.out.println("new concreteProduct!");
        }
        concreteProduct.setPlatformID(platformId);
        concreteProduct.setProductID(productId);
        concreteProduct.setShopID(shopId);
        concreteProduct.setCurrentPrice(currentPrice);
        return ResultFactory.buildSuccessResult(productService.saveConcreteProduct(concreteProduct));
    }

}
