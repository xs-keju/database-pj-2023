package com.example.backend0.service;

import com.example.backend0.entity.Account;
import com.example.backend0.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AccountService
 * @Description
 **/
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public boolean sameAccountName(String accountName){
        return accountRepository.findByAccountName(accountName).isPresent();
    }
    public Account save(Account account){
        if(account!=null){
            return accountRepository.save(account);
        }
        return null;
    }
}
