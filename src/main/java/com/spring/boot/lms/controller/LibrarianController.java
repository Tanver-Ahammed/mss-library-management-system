package com.spring.boot.lms.controller;

import com.spring.boot.lms.dto.LibrarianDTO;
import com.spring.boot.lms.dto.StudentDTO;
import com.spring.boot.lms.email.EmailSenderService;
import com.spring.boot.lms.service.impl.FileServiceImpl;
import com.spring.boot.lms.service.impl.LibrarianServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/librarian")
public class LibrarianController {

    @Autowired
    private LibrarianServiceImpl librarianService;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Value("${project.image}")
    private String path;

    @GetMapping(path = "/add")
    public String addLibrarian(Model model) {
        model.addAttribute("librarianDTO", new LibrarianDTO());
        return "add-librarian";
    }

    @PostMapping(path = "/save")
    public String saveLibrarian(@Valid @ModelAttribute("librarianDTO") LibrarianDTO librarianDTO, BindingResult result,
                                @RequestParam(value = "librarianImage", required = false) MultipartFile librarianImage,
                                Model model) throws IOException {
        if (result.hasErrors()) {
            System.err.println(result);
            model.addAttribute("librarianDTO", librarianDTO);
            return "add-librarian";
        }
        String librarianImageName = this.fileService.uploadImage(path, librarianImage);
        librarianDTO.setImage(librarianImageName);
        librarianDTO.setEnable(false);
        librarianDTO = this.librarianService.addLibrarian(librarianDTO);
//        this.emailSenderService.sendEmailWithoutAttachment(librarianDTO.getEmail(),
//                "Librarian Added", librarianDTO.getName() + " added successfully");
        model.addAttribute("studentDTO", new StudentDTO());
        return "redirect:/librarian/add";
    }

}
