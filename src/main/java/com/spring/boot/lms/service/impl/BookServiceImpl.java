package com.spring.boot.lms.service.impl;

import com.spring.boot.lms.dto.BookDTO;
import com.spring.boot.lms.entities.Book;
import com.spring.boot.lms.exception.ResourceNotFoundException;
import com.spring.boot.lms.repository.BookRepository;
import com.spring.boot.lms.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    // add book
    @Override
    public BookDTO addBook(BookDTO bookDTO, MultipartFile bookImage) throws IOException {
        String bookImageName = this.fileService.uploadImage(path, bookImage);
        bookDTO.setImage(bookImageName);
        bookDTO.setEnable(true);
        bookDTO.setStock(bookDTO.getTotal());
        Book book = this.modelMapper.map(bookDTO, Book.class);
        book = this.bookRepository.save(book);
        return this.modelMapper.map(book, BookDTO.class);
    }

    // get all book
    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = this.bookRepository.findAll();
        return books
                .stream()
                .map(book -> this.modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    // get book by id
    @Override
    public BookDTO getBookById(long bookId) {
        Book book = this.bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "Id", bookId));
        return this.modelMapper.map(book, BookDTO.class);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO, MultipartFile bookImage, long bookId) throws IOException {
        Book book = this.bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "Id", bookId));

        if (!(Objects.equals(bookImage.getOriginalFilename(), ""))) {
            boolean previousBookImageIsDeleted = this.fileService.deleteImage(path, book.getImage());
            String imageName = this.fileService.uploadImage(path, bookImage);
            book.setImage(imageName);
        }

        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());

        // stock book
        long stock = book.getStock() + (bookDTO.getTotal() - book.getTotal());
        book.setTotal(bookDTO.getTotal());
        book.setStock(stock);
        book.setEnable(book.getStock() > 0);
        return this.modelMapper.map(this.bookRepository.save(book), BookDTO.class);
    }

    @Override
    public boolean updateBook(long bookId) {
        Book book = this.bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "Id", bookId));
        long stock = book.getStock() - 1;
        book.setStock(stock);
        book.setEnable(book.getTotal() > stock);
        this.bookRepository.save(book);
        return true;
    }


}
