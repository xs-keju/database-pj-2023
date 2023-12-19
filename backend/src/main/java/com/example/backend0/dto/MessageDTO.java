package com.example.backend0.dto;

import java.util.Date;

/**
 * @ClassName MessageDTO
 * @Description
 **/
public class MessageDTO {
    String productName;
    String shopName;
    String platformName;
    Float currentPrice;
    Float minimumPrice;
    Date time;

    public MessageDTO(String productName, String shopName, String platformName, Float currentPrice, Float minimumPrice, Date time) {
        this.productName = productName;
        this.shopName = shopName;
        this.platformName = platformName;
        this.currentPrice = currentPrice;
        this.minimumPrice = minimumPrice;
        this.time = time;
    }
}
