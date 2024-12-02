package com.example.hexagonal_bank.services;

import com.example.hexagonal_bank.dtos.BankAccountDTO;
import com.example.hexagonal_bank.dtos.CurrentBankAccountDTO;
import com.example.hexagonal_bank.dtos.CustomerDTO;
import com.example.hexagonal_bank.dtos.SavingAccountDTO;
import com.example.hexagonal_bank.exceptions.BalanceNotSufficientException;
import com.example.hexagonal_bank.exceptions.BankAccountNotFoundException;
import com.example.hexagonal_bank.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
