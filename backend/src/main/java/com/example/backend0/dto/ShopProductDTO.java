package com.example.backend0.dto;

public class ShopProductDTO {
    String productName;
    String type;
    String productAddress;
    String productDate;
    String description;
    Integer shopID;
    String platformName;
    Float currentPrice;

    public ShopProductDTO(String productName, String type, String productAddress, String productDate, String description, Integer shopID, String platformName, Float currentPrice) {
        this.productName = productName;
        this.type = type;
        this.productAddress = productAddress;
        this.productDate = productDate;
        this.description = description;
        this.shopID = shopID;
        this.platformName = platformName;
        this.currentPrice = currentPrice;
    }
}
