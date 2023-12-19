package com.example.backend0.service;

import com.example.backend0.dto.FullProductInfoDTO;
import com.example.backend0.dto.PartialProductDTO;
import com.example.backend0.dto.PriceHistoryDTO;
import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.Platform;
import com.example.backend0.entity.Product;
import com.example.backend0.entity.Shop;
import com.example.backend0.repository.ConcreteProductRepository;
import com.example.backend0.repository.PriceHistoryRepository;
import com.example.backend0.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    @Autowired
    PriceHistoryRepository priceHistoryRepository;
    public List<PartialProductDTO> getAllProductsWithPartialInfo(){
        List<ConcreteProduct> concreteProducts=concreteProductRepository.findAll();
        List<PartialProductDTO> res=new ArrayList<>();
        for(ConcreteProduct product:concreteProducts){
            PartialProductDTO partialProductDTO =getPartialProductByConcreteProductId(product.getID());
            if(partialProductDTO ==null){
                return null;
            }
            res.add(partialProductDTO);
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
    public PartialProductDTO getPartialProductByConcreteProductId(Integer id){
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
        PartialProductDTO partialProductDTO =new PartialProductDTO();
        partialProductDTO.setId(id);
        partialProductDTO.setProductName(product.getProductName());
        partialProductDTO.setShopName(shop.getShopName());
        partialProductDTO.setPlatformName(platform.getPlatformName());
        partialProductDTO.setType(product.getType());
        partialProductDTO.setCurrentPrice(concreteProduct.getCurrentPrice());
        return partialProductDTO;
    }
    public FullProductInfoDTO findFullProductInfoByConcreteProductID(Integer concreteProductId){

        return concreteProductRepository.findFullProductInfoByConcreteID(concreteProductId);
    }
    public List<PriceHistoryDTO> getPriceHistoryByYear(Integer concreteProductID, Date date){
        return priceHistoryRepository.findPriceHistoryByYear(concreteProductID,date.toLocalDate().getYear());
    }
    public List<PriceHistoryDTO> getPriceHistoryByMonth(Integer concreteProductID, Date date){
        return priceHistoryRepository.findPriceHistoryByMonth(concreteProductID,date.toLocalDate().getYear(),date.toLocalDate().getMonthValue());
    }
    public List<PriceHistoryDTO> getPriceHistoryByWeek(Integer concreteProductID, Date date){
        return priceHistoryRepository.findPriceHistoryByWeek(concreteProductID,date);
    }
}
