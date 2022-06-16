package com.spring.boot.lms.service.impl;

import com.spring.boot.lms.dto.BookDTO;
import com.spring.boot.lms.dto.BorrowedBookOfStudentDTO;
import com.spring.boot.lms.dto.StudentDTO;
import com.spring.boot.lms.entities.BorrowedBookOfStudent;
import com.spring.boot.lms.repository.BorrowedBookOfStudentRepository;
import com.spring.boot.lms.service.BorrowedBookOfStudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BorrowedBookOfStudentServiceImpl implements BorrowedBookOfStudentService {

    @Autowired
    private BorrowedBookOfStudentRepository borrowedBookOfStudentRepository;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public boolean saveBorrowBookStudent(StudentDTO studentDTO, BookDTO bookDTO) {
        BorrowedBookOfStudentDTO borrowedBookOfStudentDTO = new BorrowedBookOfStudentDTO();
        borrowedBookOfStudentDTO.setStudentDTO(studentDTO);
        borrowedBookOfStudentDTO.setBookDTO(bookDTO);
        borrowedBookOfStudentDTO.setBookLiftingDate(new Date());
        borrowedBookOfStudentDTO.setStayingBook(true);

        borrowedBookOfStudentDTO = this.modelMapper
                .map(this.borrowedBookOfStudentRepository.save(this.modelMapper
                                .map(borrowedBookOfStudentDTO, BorrowedBookOfStudent.class)),
                        BorrowedBookOfStudentDTO.class);

        return borrowedBookOfStudentDTO != null;
    }
}
