package com.tikam.bankingSystem.controller;

import com.tikam.bankingSystem.dto.AccountDto;
import com.tikam.bankingSystem.service.AccountServiceImpl;
import com.tikam.bankingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountServiceImpl accountServiceImpl;
    private UserService userService;

    @Autowired
    public AccountController(AccountServiceImpl accountServiceImpl, UserService userService) {
        this.accountServiceImpl = accountServiceImpl;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<AccountDto> AddAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountServiceImpl.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountServiceImpl.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        double amount = request.get("amount");

        AccountDto accountDto = accountServiceImpl.deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        double amount = request.get("amount");

        AccountDto accountDto = accountServiceImpl.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountDto>> getList() {
        List<AccountDto> accountDtos = accountServiceImpl.getAccountHolders();
        return ResponseEntity.ok(accountDtos);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountServiceImpl.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully.");
    }

    // this is just for testing the async functionality
//    @GetMapping("/getUser")
//    public void getUserMethod() {
//        System.out.println("inside the getUserMethod " + Thread.currentThread().getName());
//        userService.asyncMethod();
//
//    }

    // for testing for the custom interceptor example
    @GetMapping("/getUser1")
    public String getUser1() {
        userService.getUser();
        return "success";
    }
}
