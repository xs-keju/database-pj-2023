package com.example.backend0.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @ClassName ConcreteProduct
 * @Description 具体商品表
 **/
@Data
@Entity
public class ConcreteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "concrete_goods_generator")
    @SequenceGenerator(name = "concrete_goods_generator",sequenceName = "concrete_goods_seq", allocationSize = 1)
    Integer ID;
    Integer shopID;
    Integer platformID;
    Integer productID;
    Float currentPrice;
}
