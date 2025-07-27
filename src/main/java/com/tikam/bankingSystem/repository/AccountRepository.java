package com.tikam.bankingSystem.repository;

import com.tikam.bankingSystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
