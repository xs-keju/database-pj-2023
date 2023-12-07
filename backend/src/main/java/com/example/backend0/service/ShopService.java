package com.example.backend0.service;

import com.example.backend0.entity.Shop;
import com.example.backend0.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShopService
 * @Description
 **/
@Service
public class ShopService {
    @Autowired
    ShopRepository shopRepository;
    public Shop save(Shop shop){
        if(shop!=null){
            return shopRepository.save(shop);
        }
        return null;
    }
    public Shop getShopById(Integer id){
        return shopRepository.findById(id).orElse(null);
    }
}
