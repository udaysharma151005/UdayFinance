package com.uday.financeportal.controller;

import com.uday.financeportal.model.Transaction;
import com.uday.financeportal.model.User;
import com.uday.financeportal.repository.TransactionRepository;
import com.uday.financeportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @GetMapping("/reports")
    public String showReports(Model model, Principal principal) {
        // 🔐 Get logged-in user
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<Transaction> all = transactionRepository.findByUserOrderByDateDesc(user);

        // 🗓 Filter current month transactions
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        List<Transaction> thisMonth = all.stream()
                .filter(t -> t.getDate() != null &&
                        t.getDate().getMonthValue() == month &&
                        t.getDate().getYear() == year)
                .toList();

        // 💰 Income and Expense totals
        double income = thisMonth.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = thisMonth.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        // 📊 Breakdown by Mode (ONLINE vs CASH)
        Map<String, Double> modeBreakdown = thisMonth.stream()
                .collect(Collectors.groupingBy(
                        t -> Optional.ofNullable(t.getMode()).orElse("OTHER").toUpperCase(),
                        TreeMap::new,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // 📂 Expense by Category
        Map<String, Double> expenseCategoryBreakdown = thisMonth.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(
                        t -> Optional.ofNullable(t.getCategory()).orElse("Other"),
                        TreeMap::new,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // 💰 Income by Category
        Map<String, Double> incomeCategoryBreakdown = thisMonth.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(
                        t -> Optional.ofNullable(t.getCategory()).orElse("Other"),
                        TreeMap::new,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // 📦 Send to frontend
        model.addAttribute("income", income);
        model.addAttribute("expense", expense);
        model.addAttribute("balance", income - expense);
        model.addAttribute("modeBreakdown", modeBreakdown);
        model.addAttribute("expenseCategoryBreakdown", expenseCategoryBreakdown);
        model.addAttribute("incomeCategoryBreakdown", incomeCategoryBreakdown);
        model.addAttribute("username", user.getName());

        return "reports";
    }
}
