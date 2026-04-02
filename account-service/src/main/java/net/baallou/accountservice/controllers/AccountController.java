package net.baallou.accountservice.controllers;

import lombok.RequiredArgsConstructor;
import net.baallou.accountservice.entities.BankAccount;
import net.baallou.accountservice.services.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public BankAccount createAccount(@RequestParam Long customerId, @RequestParam double balance) {
        return accountService.createAccount(customerId, balance);
    }

    @GetMapping("/{id}")
    public BankAccount getAccount(@PathVariable String id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/{id}/debit")
    public void debit(@PathVariable String id, @RequestParam double amount) {
        accountService.debit(id, amount);
    }

    @PostMapping("/{id}/credit")
    public void credit(@PathVariable String id, @RequestParam double amount) {
        accountService.credit(id, amount);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam String from,
                         @RequestParam String to,
                         @RequestParam double amount) {
        accountService.transfer(from, to, amount);
    }
}
