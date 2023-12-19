package com.example.backend0.repository;

import com.example.backend0.dto.PriceHistoryDTO;
import com.example.backend0.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Integer> {
    @Query("SELECT new com.example.backend0.dto.PriceHistoryDTO(ph.ID, ph.price, ph.date) " +
            "FROM PriceHistory ph " +
            "WHERE ph .concreteProductID = :concreteProductID " +
            "AND YEAR(ph.date) = :year")
    List<PriceHistoryDTO> findPriceHistoryByYear(@Param("concreteProductID") Integer concreteProductID,
                                                 @Param("year") Integer year);

//    @Query("SELECT new com.example.backend0.dto.PriceHistoryDTO(ph.ID, ph.price, ph.date) " +
//            "FROM PriceHistory ph " +
//            "WHERE ph.concreteProductID = :concreteProductID " +
//            "AND YEAR(ph.date) = :year " +
//            "AND MONTH(ph.date) = :month")
//    List<PriceHistoryDTO> findPriceHistoryByMonth(@Param("concreteProductID") Integer concreteProductID,
//                                                  @Param("year") Integer year,
//                                                  @Param("month") Integer month);
    @Query("SELECT new com.example.backend0.dto.PriceHistoryDTO(ph.ID, ph.price, ph.date) " +
            "FROM PriceHistory ph " +
            "WHERE ph.concreteProductID = :concreteProductID " +
            "AND YEAR(ph.date) = :year " +
            "AND MONTH(ph.date) = :month")
    List<PriceHistoryDTO> findPriceHistoryByMonth(@Param("concreteProductID") Integer concreteProductID,
                                                  @Param("year") Integer year,
                                                  @Param("month") Integer month);

    @Query("SELECT new com.example.backend0.dto.PriceHistoryDTO(ph.ID, ph.price, ph.date) " +
            "FROM PriceHistory ph " +
            "WHERE ph.concreteProductID = :concreteProductID " +
            "AND YEARWEEK(ph.date) = YEARWEEK(:date)")
    List<PriceHistoryDTO> findPriceHistoryByWeek(@Param("concreteProductID") Integer concreteProductID,
                                                 @Param("date") Date date);
}
