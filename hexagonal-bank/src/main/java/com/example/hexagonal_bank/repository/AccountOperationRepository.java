package com.example.hexagonal_bank.repository;

import com.example.hexagonal_bank.model.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
  Optional<List<AccountOperation>> findByBankAccountId(String bankAccountId);
}
