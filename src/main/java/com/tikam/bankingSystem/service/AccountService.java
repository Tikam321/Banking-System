package com.tikam.bankingSystem.service;

import com.tikam.bankingSystem.dto.AccountDto;
import com.tikam.bankingSystem.entity.Account;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long Id);
    List<AccountDto> getAccountHolders();

    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id, double amount);

    void deleteAccount(Long id);


}
