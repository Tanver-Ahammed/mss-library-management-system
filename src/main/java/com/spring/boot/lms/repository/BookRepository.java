package com.spring.boot.lms.repository;

import com.spring.boot.lms.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
