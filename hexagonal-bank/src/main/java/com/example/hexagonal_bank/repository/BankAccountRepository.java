package com.example.hexagonal_bank.repository;

import com.example.hexagonal_bank.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
