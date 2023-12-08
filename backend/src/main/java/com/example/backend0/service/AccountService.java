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
    public boolean accountNameIsPresent(String accountName){
        return accountRepository.findByAccountName(accountName).isPresent();
    }
    public boolean rightAccount(String accountName,String password){
        Account account=accountRepository.findByAccountName(accountName).orElse(null);
        if(account!=null&&account.getPassword().equals(password)){
            return true;
        }
        return false;
    }
    public Account save(Account account){
        if(account!=null){
            return accountRepository.save(account);
        }
        return null;
    }
    public Account getAccountByAccountName(String accountName){
        return accountRepository.findByAccountName(accountName).orElse(null);
    }
}
