package com.tikam.bankingSystem.service;

import com.tikam.bankingSystem.dto.AccountDto;
import com.tikam.bankingSystem.entity.Account;
import com.tikam.bankingSystem.mapper.AccountMapper;
import com.tikam.bankingSystem.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tikam.bankingSystem.mapper.AccountMapper.getAllAccountDto;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccountJpa(accountDto);
         Account savedAccount  = accountRepository.save(account);
         return AccountMapper.mapToAccountDto(savedAccount);
    }

    public List<AccountDto> getAccountHolders() {
        List<Account> users = accountRepository.findAll();
        return users.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());

    }

    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        double totalBalance = account.getBalance() + amount;
        account.setBalance(totalBalance);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double withDrawAmount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));

        if (withDrawAmount > account.getBalance()) {
            throw new RuntimeException("Insufficient Amount");
        }

        double totalBalance = account.getBalance() - withDrawAmount;
        account.setBalance(totalBalance);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);
    }

    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);

    }






//    public AccountDto deleteAccountById(Long Id) {
//        accountRepository.deleteById(Id);
//
//    }




}
