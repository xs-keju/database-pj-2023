package com.example.backend0.dto;

/**
 * @ClassName FavotiteDTO
 * @Description
 **/
public class FavoriteDTO {
    String productName;
    String shopName;
    String platformName;
    Float currentPrice;
    Float minimumPrice;

    public FavoriteDTO(String productName, String shopName, String platformName, Float currentPrice, Float minimumPrice) {
        this.productName = productName;
        this.shopName = shopName;
        this.platformName = platformName;
        this.currentPrice = currentPrice;
        this.minimumPrice = minimumPrice;
    }
}
