package com.example.backend0.service;

import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.Platform;
import com.example.backend0.entity.Product;
import com.example.backend0.entity.Shop;
import com.example.backend0.repository.ConcreteProductRepository;
import com.example.backend0.repository.PlatformRepository;
import com.example.backend0.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProductService
 * @Description
 **/
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ConcreteProductRepository concreteProductRepository;
    @Autowired
    PlatformService platformService;
    @Autowired
    ShopService shopService;
    public List<PartialProduct> getAllProductsWithPartialInfo(){
        List<ConcreteProduct> concreteProducts=concreteProductRepository.findAll();
        List<PartialProduct> res=new ArrayList<>();
        for(ConcreteProduct product:concreteProducts){
            PartialProduct partialProduct=getPartialProductByConcreteProductId(product.getID());
            if(partialProduct==null){
                return null;
            }
            res.add(partialProduct);
        }
        return res;
    }
    public List<Product> getAllProductsWithFullInfo(){
        return productRepository.findAll();
    }
    public Product save(Product product){
        Product res=null;
        if(product!=null){
            res=productRepository.save(product);
        }
        return res;
    }
    public ConcreteProduct saveConcreteProduct(ConcreteProduct concreteProduct){
        ConcreteProduct res=null;
        if(concreteProduct!=null){
            res=concreteProductRepository.save(concreteProduct);
        }
        return res;

    }
    public Product getProductById(Integer id){
        return productRepository.findById(id).orElse(null);
    }
    public PartialProduct getPartialProductByConcreteProductId(Integer id){
        ConcreteProduct concreteProduct=concreteProductRepository.findById(id).orElse(null);
        if(concreteProduct==null){
            return null;
        }
        Platform platform=platformService.getPlatformById(concreteProduct.getPlatformID());
        if(platform==null){
            return null;
        }
        Product product=productRepository.findById(concreteProduct.getProductID()).orElse(null);
        if(product==null){
            return null;
        }
        Shop shop=shopService.getShopById(concreteProduct.getShopID());
        if(shop==null){
            return null;
        }
        PartialProduct partialProduct=new PartialProduct();
        partialProduct.setId(id);
        partialProduct.setProductName(product.getProductName());
        partialProduct.setShopName(shop.getShopName());
        partialProduct.setPlatformName(platform.getPlatformName());
        partialProduct.setType(product.getType());
        partialProduct.setCurrentPrice(concreteProduct.getCurrentPrice());
        return partialProduct;
    }
}
