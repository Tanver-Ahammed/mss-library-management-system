package com.spring.boot.lms.service.impl;

import com.spring.boot.lms.dto.LibrarianDTO;
import com.spring.boot.lms.entities.Librarian;
import com.spring.boot.lms.exception.ResourceNotFoundException;
import com.spring.boot.lms.repository.LibrarianRepository;
import com.spring.boot.lms.service.LibrarianService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private ModelMapper modelMapper;

    // add librarian
    @Override
    public LibrarianDTO addLibrarian(LibrarianDTO librarianDTO) {
        Librarian librarian = this.modelMapper.map(librarianDTO, Librarian.class);
        librarian = this.librarianRepository.save(librarian);
        return this.modelMapper.map(librarian, LibrarianDTO.class);
    }

    // get all librarian
    public List<LibrarianDTO> getAllLibrarians() {
        List<Librarian> librarians = this.librarianRepository.findAll();
        return librarians
                .stream()
                .map(librarian -> this.modelMapper.map(librarian, LibrarianDTO.class))
                .collect(Collectors.toList());
    }

    // add librarian by id
    public LibrarianDTO getLibrarianById(long librarianId) {
        Librarian librarian = this.librarianRepository.findById(librarianId).orElseThrow(() ->
                new ResourceNotFoundException("Librarian", "Id", librarianId));
        return this.modelMapper.map(librarian, LibrarianDTO.class);
    }

}
