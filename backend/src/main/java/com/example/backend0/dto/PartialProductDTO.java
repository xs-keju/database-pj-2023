package com.example.backend0.dto;

import lombok.Data;

@Data
public
class PartialProductDTO {
    Integer id;
    String productName;
    String shopName;
    String platformName;
    String type;
    Float currentPrice;
}
