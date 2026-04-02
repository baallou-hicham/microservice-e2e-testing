package net.baallou.accountservice.services;

import net.baallou.accountservice.entities.BankAccount;

import java.util.List;

public interface AccountService {
    BankAccount createAccount(Long customerId, double initialBalance);

    BankAccount getAccount(String accountId);

    List<BankAccount> getAccountsByCustomer(Long customerId);

    void debit(String accountId, double amount);

    void credit(String accountId, double amount);

    void transfer(String fromAccount, String toAccount, double amount);
}
