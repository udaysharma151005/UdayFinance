package com.uday.financeportal.repository;
import com.uday.financeportal.model.LendBorrowRecord;
import com.uday.financeportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LendBorrowRecordRepository extends JpaRepository<LendBorrowRecord, Long> {
    List<LendBorrowRecord> findByUserOrderByDateDesc(User user);
}