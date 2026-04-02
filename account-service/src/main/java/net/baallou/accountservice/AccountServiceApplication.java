package net.baallou.accountservice;

import net.baallou.accountservice.entities.BankAccount;
import net.baallou.accountservice.entities.enums.AccountStatus;
import net.baallou.accountservice.entities.enums.AccountType;
import net.baallou.accountservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@EnableFeignClients
@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Profile("!test")
    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository accountRepository) {
        return args -> {
            BankAccount bankAccount1 = BankAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .balance(98000)
                    .createdAt(new Date())
                    .type(AccountType.CURRENT_ACCOUNT)
                    .status(AccountStatus.ACTIVATED)
                    .customerId(Long.valueOf(1))
                    .build();
            BankAccount bankAccount2 = BankAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .balance(12000)
                    .createdAt(new Date())
                    .type(AccountType.SAVING_ACCOUNT)
                    .status(AccountStatus.CREATED)
                    .customerId(Long.valueOf(2))
                    .build();
            accountRepository.save(bankAccount1);
            accountRepository.save(bankAccount2);
        };
    }
}
