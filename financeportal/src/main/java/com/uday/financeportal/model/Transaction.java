package com.uday.financeportal.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;      // INCOME or EXPENSE
    private String category;
    private Double amount;
    private LocalDate date;
    private String note;
    private String mode;      // ONLINE or CASH
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}