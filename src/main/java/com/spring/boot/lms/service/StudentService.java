package com.spring.boot.lms.service;

import com.spring.boot.lms.dto.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StudentService {

    StudentDTO addStudent(StudentDTO studentDTO, MultipartFile studentImage) throws IOException;

    StudentDTO getStudentById(long studentId);

    boolean verifyStudent(long id, String verifyCode);

    boolean updateStudent(StudentDTO studentDTO, MultipartFile studentImage, long studentId) throws IOException;

}
