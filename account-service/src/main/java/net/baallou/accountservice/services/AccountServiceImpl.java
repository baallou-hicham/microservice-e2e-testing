package net.baallou.accountservice.services;

import lombok.RequiredArgsConstructor;
import net.baallou.accountservice.clients.CustomerClient;
import net.baallou.accountservice.dto.CustomerDTO;
import net.baallou.accountservice.entities.*;
import net.baallou.accountservice.entities.enums.AccountStatus;
import net.baallou.accountservice.entities.enums.AccountType;
import net.baallou.accountservice.entities.enums.OperationType;
import net.baallou.accountservice.repository.AccountOperationRepository;
import net.baallou.accountservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository accountRepository;
    private final AccountOperationRepository operationRepository;
    private final CustomerClient customerClient;

    @Override
    public BankAccount createAccount(Long customerId, double initialBalance) {

        // 🔍 vérifier client
        CustomerDTO customer = customerClient.getCustomerById(customerId);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        BankAccount account = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .status(AccountStatus.CREATED)
                .type(AccountType.CURRENT_ACCOUNT)
                .balance(initialBalance)
                .customerId(customerId)
                .build();

        return accountRepository.save(account);
    }

    @Override
    public BankAccount getAccount(String accountId) {
        return null;
    }

    @Override
    public List<BankAccount> getAccountsByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public void debit(String accountId, double amount) {
        BankAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        operationRepository.save(AccountOperation.builder()
                .operationDate(new Date())
                .amount(amount)
                .type(OperationType.DEBIT)
                .bankAccount(account)
                .build());
    }

    @Override
    public void credit(String accountId, double amount) {
        BankAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        operationRepository.save(AccountOperation.builder()
                .operationDate(new Date())
                .amount(amount)
                .type(OperationType.CREDIT)
                .bankAccount(account)
                .build());
    }

    @Override
    public void transfer(String from, String to, double amount) {
        debit(from, amount);
        credit(to, amount);
    }
}