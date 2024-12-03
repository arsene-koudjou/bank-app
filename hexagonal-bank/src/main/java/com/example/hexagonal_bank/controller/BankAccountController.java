package com.example.hexagonal_bank.controller;

import com.example.hexagonal_bank.dtos.BankAccountDTO;
import com.example.hexagonal_bank.dtos.CreateBankAccountDTO;
import com.example.hexagonal_bank.exceptions.CustomerNotFoundException;
import com.example.hexagonal_bank.model.BankAccount;
import com.example.hexagonal_bank.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/bank")
public class BankAccountController {
    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/create-bank-account")
    public void createCustomerBankAccount(@RequestBody CreateBankAccountDTO createBankAccountDTO) throws CustomerNotFoundException {
         bankAccountService.createNewBankAccount(createBankAccountDTO);
    }

    @GetMapping("/list-account/{id}")
    public List<BankAccountDTO> getBankList(@PathVariable Long id){
        return bankAccountService.bankAccountList(id);
    }
}
