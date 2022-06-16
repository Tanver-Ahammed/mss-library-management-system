package com.spring.boot.lms.repository;

import com.spring.boot.lms.entities.BorrowedBookOfStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBookOfStudentRepository extends JpaRepository<BorrowedBookOfStudent, Long> {
}
