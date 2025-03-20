package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    // @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerUser(Account account) throws IllegalArgumentException {
        if(account.getUsername() != null && account.getUsername() != "" 
        && account.getPassword().length() >= 4 
        && accountRepository.findByUsername((account.getUsername())) == null) {
            return accountRepository.save(account);
        }
        return null;
    }

    //List<Account> getAccounts();

    public Account getAccountById(Integer id){
        Optional<Account> optAcc = accountRepository.findById(id);
        return optAcc.orElse(null);
    };
    public Account saveAccount(Account account){
        return accountRepository.save(account);
    };

    public Account getAccByUsername(String username){
        return accountRepository.findByUsername(username);
    };

}
