package com.spring.boot.lms.repository;

import com.spring.boot.lms.entities.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
