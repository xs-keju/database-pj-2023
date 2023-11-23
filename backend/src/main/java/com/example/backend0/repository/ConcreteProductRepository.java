package com.example.backend0.repository;

import com.example.backend0.entity.ConcreteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcreteProductRepository extends JpaRepository<ConcreteProduct,Integer> {
}
