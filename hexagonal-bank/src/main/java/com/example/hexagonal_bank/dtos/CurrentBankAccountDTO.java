package com.example.hexagonal_bank.dtos;

import lombok.Data;

import java.util.Date;
@Data
public class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private CustomerDTO customerDTO;
    private double overDraft;
}
