package com.spring.boot.lms.repository;

import com.spring.boot.lms.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
