package com.uday.financeportal.controller;
import com.uday.financeportal.model.LendBorrowRecord;
import com.uday.financeportal.model.User;
import com.uday.financeportal.repository.LendBorrowRecordRepository;
import com.uday.financeportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
@Controller
@RequiredArgsConstructor
public class LendBorrowController {
    private final LendBorrowRecordRepository recordRepository;
    private final UserRepository userRepository;
    @GetMapping("/ledger")
    public String ledgerPage(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<LendBorrowRecord> records = recordRepository.findByUserOrderByDateDesc(user);
        double totalLent = records.stream()
                .filter(r -> "LENT".equals(r.getType()))
                .mapToDouble(LendBorrowRecord::getAmount)
                .sum();
        double totalBorrowed = records.stream()
                .filter(r -> "BORROW".equals(r.getType()))
                .mapToDouble(LendBorrowRecord::getAmount)
                .sum();
        model.addAttribute("records", records);
        model.addAttribute("totalLent", totalLent);
        model.addAttribute("totalBorrowed", totalBorrowed);
        model.addAttribute("netBalance", totalLent - totalBorrowed);
        model.addAttribute("newRecord", new LendBorrowRecord());
        model.addAttribute("username", user.getName()); // ✅ Inject user's name
        return "ledger";
    }
    @PostMapping("/add-record")
    public String addRecord(@ModelAttribute LendBorrowRecord newRecord, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        newRecord.setUser(user);
        if (newRecord.getDate() == null) {
            newRecord.setDate(LocalDate.now());
        }
        recordRepository.save(newRecord);
        return "redirect:/ledger";
    }
    @GetMapping("/delete-record/{id}")
    public String deleteRecord(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        LendBorrowRecord record = recordRepository.findById(id).orElseThrow();
        if (record.getUser().getId().equals(user.getId())) {
            recordRepository.delete(record);
        }
        return "redirect:/ledger";
    }
    @PostMapping("/update-record/{id}")
    public String updateRecord(@PathVariable Long id, @ModelAttribute LendBorrowRecord updated, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        LendBorrowRecord record = recordRepository.findById(id).orElseThrow();
        if (record.getUser().getId().equals(user.getId())) {
            record.setPersonName(updated.getPersonName());
            record.setType(updated.getType());
            record.setAmount(updated.getAmount());
            record.setDate(updated.getDate());
            record.setNote(updated.getNote());
            recordRepository.save(record);
        }
        return "redirect:/ledger";
    }
}