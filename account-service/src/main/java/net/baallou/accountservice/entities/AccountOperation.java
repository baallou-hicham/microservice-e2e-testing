package net.baallou.accountservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.baallou.accountservice.entities.enums.OperationType;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date operationDate;

    private double amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private OperationType type; // DEBIT, CREDIT

    @ManyToOne
    private BankAccount bankAccount;
}