package com.spring.boot.lms.service;

import com.spring.boot.lms.dto.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface BookService {

    BookDTO addBook(BookDTO book, MultipartFile bookImage) throws IOException;

    List<BookDTO> getAllBooks();

    BookDTO getBookById(long bookId);

    BookDTO updateBook(BookDTO bookDTO, MultipartFile bookImage, long bookId) throws IOException;

    boolean updateBook(long bookId);

}
