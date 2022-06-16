package com.spring.boot.lms.service;

import com.spring.boot.lms.dto.LibrarianDTO;
import org.springframework.stereotype.Service;

@Service
public interface LibrarianService {

    LibrarianDTO addLibrarian(LibrarianDTO librarianDTO);

}
