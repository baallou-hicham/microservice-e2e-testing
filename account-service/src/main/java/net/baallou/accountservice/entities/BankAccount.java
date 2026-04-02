package net.baallou.accountservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import net.baallou.accountservice.entities.enums.AccountStatus;
import net.baallou.accountservice.entities.enums.AccountType;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BankAccount {

    @Id
    private String id;

    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private AccountType type; // CURRENT_ACCOUNT, SAVING_ACCOUNT

    @Enumerated(EnumType.STRING)
    private AccountStatus status; // CREATED, ACTIVATED, SUSPENDED, BLOCKED

    private double balance;

    private Long customerId; // référence Customer-Service
}