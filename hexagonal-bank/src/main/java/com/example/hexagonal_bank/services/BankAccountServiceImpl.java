package com.example.hexagonal_bank.services;

import com.example.hexagonal_bank.dtos.BankAccountDTO;
import com.example.hexagonal_bank.dtos.CurrentBankAccountDTO;
import com.example.hexagonal_bank.dtos.CustomerDTO;
import com.example.hexagonal_bank.dtos.SavingAccountDTO;
import com.example.hexagonal_bank.exceptions.BalanceNotSufficientException;
import com.example.hexagonal_bank.exceptions.BankAccountNotFoundException;
import com.example.hexagonal_bank.exceptions.CustomerNotFoundException;
import com.example.hexagonal_bank.mappers.BankAccountMapper;
import com.example.hexagonal_bank.model.CurrentAccount;
import com.example.hexagonal_bank.model.Customer;
import com.example.hexagonal_bank.repository.BankAccountRepository;
import com.example.hexagonal_bank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Data
@Slf4j
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{

    private static final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountMapper dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreationDate(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return List.of();
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        return List.of();
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void deleteCustomer(Long customerId) {

    }
}
