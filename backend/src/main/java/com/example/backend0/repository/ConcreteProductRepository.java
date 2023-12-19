package com.example.backend0.repository;

import com.example.backend0.dto.FullProductInfoDTO;
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
    @Query("SELECT cp from ConcreteProduct cp where cp.shopID=:shopID")
    List<ConcreteProduct> getConcreteProductsByShopID(Integer shopID);
@Query("select cp from ConcreteProduct cp where cp.shopID=:shopID and " +
        "cp.platformID=:platformID and " +
        "cp.productID=:productID and " +
        "cp.currentPrice=:currentPrice ")
    ConcreteProduct findByAllInfo(    Integer shopID,
                                      Integer platformID,
                                      Integer productID,
                                      Float currentPrice);
}
