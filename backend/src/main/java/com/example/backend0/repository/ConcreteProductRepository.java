package com.example.backend0.repository;

import com.example.backend0.dto.FullProductInfoDTO;
import com.example.backend0.dto.ShopProductDTO;
import com.example.backend0.entity.ConcreteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcreteProductRepository extends JpaRepository<ConcreteProduct,Integer> {
    @Query("SELECT new com.example.backend0.dto.FullProductInfoDTO(cp.ID,p.productName,s.shopName,s.shopAddress,pf.platformName,p.type,cp.currentPrice,p.productAddress,p.productDate,p.description) " +
            "FROM ConcreteProduct cp " +
            "JOIN Product p ON cp.productID = p.ID " +
            "JOIN Shop s ON cp.shopID = s.ID " +
            "JOIN Platform pf ON cp.platformID = pf.ID " +
            "WHERE cp.ID = :concreteProductID")
    FullProductInfoDTO findFullProductInfoByConcreteID(Integer concreteProductID);
    /*
    String productName, String type, String productAddress, String productDate,
    String description, Integer shopID, String platformName, Float currentPrice
     */
    @Query("SELECT new com.example.backend0.dto.ShopProductDTO(p.productName,p.type,p.productAddress,p.productDate,p.description,cp.shopID,pf.platformName,cp.currentPrice) from ConcreteProduct cp JOIN Product p on cp.productID=p.ID  JOIN Platform pf on pf.ID=cp.platformID where cp.shopID=:shopID")
    List<ShopProductDTO> getConcreteProductsByShopID(Integer shopID);
@Query("select cp from ConcreteProduct cp where cp.shopID=:shopID and " +
        "cp.platformID=:platformID and " +
        "cp.productID=:productID and " +
        "cp.currentPrice=:currentPrice ")
    ConcreteProduct findByAllInfo(    Integer shopID,
                                      Integer platformID,
                                      Integer productID,
                                      Float currentPrice);
}
