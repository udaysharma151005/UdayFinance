package com.uday.financeportal.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LendBorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personName;
    private String type; // LENT / BORROW
    private Double amount;
    private LocalDate date;
    private String note;
    @ManyToOne
    private User user;
}