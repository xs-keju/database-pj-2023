package com.example.backend0.repository;

import com.example.backend0.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Integer> {
}
