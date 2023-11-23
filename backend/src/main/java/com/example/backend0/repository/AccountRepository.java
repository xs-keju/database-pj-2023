package com.example.backend0.repository;

import com.example.backend0.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByAccountName(String accountName);
}
