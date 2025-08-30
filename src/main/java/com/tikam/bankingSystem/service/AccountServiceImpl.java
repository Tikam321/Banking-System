package com.tikam.bankingSystem.service;

import com.tikam.bankingSystem.RedisService.RedisService;
import com.tikam.bankingSystem.dto.AccountDto;
import com.tikam.bankingSystem.entity.Account;
import com.tikam.bankingSystem.mapper.AccountMapper;
import com.tikam.bankingSystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final RedisService redisService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RedisService redisService) {
        this.accountRepository = accountRepository;
        this.redisService = redisService;
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
        Account account1 = redisService.get(Long.toString(id), Account.class);
        if (account1 != null) {
            System.out.println("the value fetched from cache");
            return AccountMapper.mapToAccountDto(account1);
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        System.out.println("fom db get the acount obj" + account);
//        System.out.println(account);
        redisService.setAccount(Long.toString(id), account, 100l);

        System.out.println("the Local db is hit");
        return AccountMapper.mapToAccountDto(account);
    }


//    public AccountDto deleteAccountById(Long Id) {
//        accountRepository.deleteById(Id);
//
//    }




}
