package net.baallou.accountservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.baallou.accountservice.dto.CustomerDTO;
import net.baallou.accountservice.entities.enums.AccountStatus;
import net.baallou.accountservice.entities.enums.AccountType;

import java.time.LocalDate;
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

    private double balance;

    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private AccountType type; // CURRENT_ACCOUNT, SAVING_ACCOUNT

    @Enumerated(EnumType.STRING)
    private AccountStatus status; // CREATED, ACTIVATED, SUSPENDED, BLOCKED

    @Transient
    private CustomerDTO customer;
    private Long customerId; // référence Customer-Service
}