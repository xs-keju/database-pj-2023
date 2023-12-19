package com.example.backend0.repository;

import com.example.backend0.dto.MessageDTO;
import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT new com.example.backend0.dto.MessageDTO(p .productName,s .shopName,pf .platformName,cp .currentPrice,c.minimumPrice,m .date) "+
            "from Message m,ConcreteProduct cp,Product p,Collect c,Shop s,Platform pf "+
            "where m.userID=:userID and m.concreteProductID=cp.ID "+
            "and cp.productID=p .ID and c .concreteProductID=cp .ID "+
            "and s.ID=cp .shopID and pf .ID=cp .platformID")
    List<MessageDTO> getMessagesByUserID(Integer userID);

//    @Query("SELECT new com.example.backend0.dto.MessageDTO(Product.productName,Shop.shopName,Platform.platformName,ConcreteProduct.currentPrice,Collect.minimumPrice,Message.date) "+
//            "from Message,ConcreteProduct,Product,Collect,Shop,Platform "+
//            "where Message.userID=:userID and Message.concreteProductID=ConcreteProduct.ID "+
//            "and ConcreteProduct.productID=Product.ID and Collect.concreteProductID=ConcreteProduct.ID "+
//            "and Shop.ID=ConcreteProduct.shopID and Platform.ID=ConcreteProduct.platformID")
//    List<MessageDTO> getMessagesByUserID(Integer userID);
}
