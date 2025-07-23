package com.uday.financeportal.repository;

import com.uday.financeportal.model.Transaction;
import com.uday.financeportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map; // For Map<String, Double> return type

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByDateDesc(User user);

    // ✅ New: Category-wise spending for a user within a date range
    @Query("SELECT t.category AS category, SUM(t.amount) AS totalAmount " +
            "FROM Transaction t " +
            "WHERE t.user = :user AND t.type = 'EXPENSE' " +
            "AND t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY t.category " +
            "ORDER BY totalAmount DESC")
    List<Map<String, Object>> findCategorySpendingByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // ✅ New: Monthly income and expense for a user within a date range
    @Query("SELECT " +
            "FUNCTION('YEAR', t.date) AS year, " +
            "FUNCTION('MONTH', t.date) AS month, " +
            "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) AS totalIncome, " +
            "SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) AS totalExpense " +
            "FROM Transaction t " +
            "WHERE t.user = :user AND t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('YEAR', t.date), FUNCTION('MONTH', t.date) " +
            "ORDER BY year ASC, month ASC")
    List<Map<String, Object>> findMonthlySummaryByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
