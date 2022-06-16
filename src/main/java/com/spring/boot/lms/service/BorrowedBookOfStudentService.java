package com.spring.boot.lms.service;

import com.spring.boot.lms.dto.BookDTO;
import com.spring.boot.lms.dto.StudentDTO;
import org.springframework.stereotype.Service;

@Service
public interface BorrowedBookOfStudentService {

    boolean saveBorrowBookStudent(StudentDTO studentDTO, BookDTO bookDTO);

}
