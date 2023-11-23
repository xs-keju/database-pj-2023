package com.example.backend0.repository;

import com.example.backend0.entity.ConcreteProduct;
import com.example.backend0.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}
