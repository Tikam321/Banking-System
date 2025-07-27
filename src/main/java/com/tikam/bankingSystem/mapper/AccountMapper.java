package com.tikam.bankingSystem.mapper;

import com.tikam.bankingSystem.dto.AccountDto;
import com.tikam.bankingSystem.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {
    public static Account mapToAccountJpa(AccountDto accountDto) {
            String accountHolderName = accountDto.getAccountHolderName();
            Long accountId = accountDto.getId();
            double balance = accountDto.getBalance();
        return new Account(accountId, accountHolderName, balance);
    }

    public static AccountDto mapToAccountDto(Account account) {

        return new AccountDto(account.getId(), account.getAccountHolderName(), account.getBalance());
    }

    public static List<AccountDto>  getAllAccountDto(List<Account> accounts) {

        List<AccountDto> accountDtos = new ArrayList<>();
        for(Account account: accounts) {
            accountDtos.add(mapToAccountDto(account));
        }
        return accountDtos;
    }
}
