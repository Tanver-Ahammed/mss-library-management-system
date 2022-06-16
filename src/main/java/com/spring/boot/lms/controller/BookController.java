package com.spring.boot.lms.controller;

import com.spring.boot.lms.dto.BookDTO;
import com.spring.boot.lms.dto.StudentDTO;
import com.spring.boot.lms.service.impl.BookServiceImpl;
import com.spring.boot.lms.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping(path = "/book/")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    @GetMapping(path = "/add")
    public String addBook(Model model) {
        BookDTO bookDTO = new BookDTO();
        model.addAttribute("bookDTO", bookDTO);
        return "add-book";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute(value = "bookDTO") BookDTO bookDTO, BindingResult result,
                           @RequestParam(value = "bookImage", required = false) MultipartFile bookImage,
                           Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("bookDTO", bookDTO);
            return "add-book";
        }
        bookDTO = this.bookService.addBook(bookDTO, bookImage);    // save the book database
        model.addAttribute("bookDTO", new StudentDTO());
        return "redirect:/book/add";
    }

    //get add book
    @GetMapping(path = "/all")
    public String getAllBooks(Model model) {
        List<BookDTO> bookDTOS = this.bookService.getAllBooks();
        model.addAttribute("bookDTOS", bookDTOS);
        return "show-all-books";
    }

    // get a single book
    @GetMapping("/get/{bookId}")
    public String getSingleBook(@PathVariable("bookId") long bookId, Model model) {
        BookDTO bookDTO = this.bookService.getBookById(bookId);
        model.addAttribute("bookDTO", bookDTO);
        return "show-single-book";
    }

    // edit book details
    @GetMapping(path = "/edit/{bookId}")
    public String editBook(@PathVariable("bookId") long bookId, Model model) {
        BookDTO bookDTO = this.bookService.getBookById(bookId);
        System.err.println(bookDTO);
        model.addAttribute("bookDTO", bookDTO);
        return "update-book";
    }

    // update book
    @PostMapping("/update/{bookId}")
    public String updateBookSuccess(@Valid @ModelAttribute(value = "bookDTO") BookDTO bookDTO, BindingResult result,
                                    @RequestParam(value = "bookImage", required = false) MultipartFile bookImage,
                                    @PathVariable("bookId") long bookId,
                                    Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("bookDTO", bookDTO);
            return "update-book";
        }
        bookDTO = this.bookService.updateBook(bookDTO, bookImage, bookId);    // save the book database
        model.addAttribute("bookDTO", new StudentDTO());
        return "redirect:/book/all";
    }

    // get image
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
