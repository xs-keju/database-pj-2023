package com.example.backend0.service;

import lombok.Data;

@Data
public
class PartialProduct {
    Integer id;
    String productName;
    String shopName;
    String platformName;
    String type;
    Float currentPrice;
}
