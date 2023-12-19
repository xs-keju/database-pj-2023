package com.example.backend0.repository;

import com.example.backend0.dto.FavoriteDTO;
import com.example.backend0.dto.FullProductInfoDTO;
import com.example.backend0.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect,Integer> {
    @Query("SELECT new com.example.backend0.dto.FavoriteDTO(p .productName,s.shopName,pf .platformName,cp.currentPrice,c .minimumPrice) "+
            "FROM Collect c "+
            "JOIN ConcreteProduct  cp on cp .ID=c.concreteProductID "+
            "JOIN Shop s on s.ID=cp .shopID "+
            "JOIN Platform pf on pf .ID=cp .platformID " +
            "join Product p on p.ID=cp .productID "+
            "where c.userID=:userID ")
    List<FavoriteDTO> getFavoritesByUserID(Integer userID);

}

