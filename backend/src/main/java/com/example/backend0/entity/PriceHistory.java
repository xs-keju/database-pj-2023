package com.example.backend0.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

/**
 * @ClassName PriceHistory
 * @Description 价格变动表
 **/
@Data
@Entity
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_history_generator")
    @SequenceGenerator(name = "price_history_generator",sequenceName = "price_history_seq", allocationSize = 1)
    Integer ID;
    Integer concreteProductID;
    Float price;
    Date date;
}
