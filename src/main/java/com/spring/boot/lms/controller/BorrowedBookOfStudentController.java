package com.spring.boot.lms.controller;

import com.spring.boot.lms.dto.BookDTO;
import com.spring.boot.lms.dto.StudentDTO;
import com.spring.boot.lms.service.impl.BookServiceImpl;
import com.spring.boot.lms.service.impl.BorrowedBookOfStudentServiceImpl;
import com.spring.boot.lms.service.impl.FileServiceImpl;
import com.spring.boot.lms.service.impl.StudentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/borrow")
public class BorrowedBookOfStudentController {

    @Autowired
    private BorrowedBookOfStudentServiceImpl borrowedBookOfStudentService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping(path = "/student/{studentId}")
    public String studentBorrowingBook(@PathVariable("studentId") long studentId, Model model) {
        StudentDTO studentDTO = this.studentService.getStudentById(studentId);
        model.addAttribute("studentDTO", studentDTO);
        List<BookDTO> bookDTOS = this.bookService.getAllBooks()
                .stream()
                .filter(bookDTO -> bookDTO.getStock() > 0 && bookDTO.isEnable())
                .collect(Collectors.toList());
        model.addAttribute("bookDTOS", bookDTOS);
        return "student-borrow-book";
    }

    @GetMapping(path = "/student/{studentId}/book/{bookId}")
    public String studentBorrowingBookSuccessfully(@PathVariable("studentId") long studentId,
                                                   @PathVariable("bookId") long bookId,
                                                   Model model) {
        StudentDTO studentDTO = this.studentService.getStudentById(studentId);
        BookDTO bookDTO = this.bookService.getBookById(bookId);

        if (bookDTO.getStock() == 0) {
            model.addAttribute("message","This book is out of stock!!!");
            return "";
        }

        boolean isBorrowed = this.borrowedBookOfStudentService.saveBorrowBookStudent(studentDTO, bookDTO);
        if (isBorrowed) {
            this.bookService.updateBook(bookId);
        }
        return "home";
    }

}
