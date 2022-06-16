package com.spring.boot.lms.service.impl;

import com.spring.boot.lms.config.AppConstants;
import com.spring.boot.lms.dto.StudentDTO;
import com.spring.boot.lms.email.EmailSenderService;
import com.spring.boot.lms.entities.Student;
import com.spring.boot.lms.exception.ResourceNotFoundException;
import com.spring.boot.lms.repository.StudentRepository;
import com.spring.boot.lms.service.StudentService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    // add student
    @Override
    public StudentDTO addStudent(StudentDTO studentDTO, MultipartFile studentImage) throws IOException {
        String studentImageName = this.fileService.uploadImage(path, studentImage);
        studentDTO.setImage(studentImageName);
        studentDTO.setEnable(false);
        String verificationCode = RandomString.make(64);
        studentDTO.setVerificationCode(verificationCode);
        Student student = this.modelMapper.map(studentDTO, Student.class);
        studentDTO = this.modelMapper.map(this.studentRepository.save(student), StudentDTO.class);
        String siteURL = AppConstants.host + "/student/verify";
        sendVerificationEmail(studentDTO, siteURL);
        return this.modelMapper.map(student, StudentDTO.class);
    }

    // get all student
    public List<StudentDTO> getAllStudents() {
        List<Student> students = this.studentRepository.findAll();
        return students
                .stream()
                .map(student -> this.modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    // get student by id
    @Override
    public StudentDTO getStudentById(long studentId) {
        Student student = this.studentRepository.findById(studentId).orElseThrow(() ->
                new ResourceNotFoundException("Student", "Id", studentId));
        return this.modelMapper.map(student, StudentDTO.class);
    }

    // verifying student by email
    @Override
    public boolean verifyStudent(long id, String verifyCode) {
        StudentDTO studentDTO = getStudentById(id);
        if (studentDTO.getVerificationCode().equals(verifyCode)) {
            studentDTO.setEnable(true);
            studentDTO.setVerificationCode(RandomString.make(64));
            this.studentRepository.save(this.modelMapper.map(studentDTO, Student.class));
            return true;
        }
        return false;
    }

    // update student
    @Override
    public boolean updateStudent(StudentDTO studentDTO, MultipartFile studentImage, long studentId) throws IOException {
        Student student = this.studentRepository.findById(studentId).orElseThrow(() ->
                new ResourceNotFoundException("Student", "Id", studentId));
        if (!student.getPassword().equals(studentDTO.getPassword()))
            return false;

        if (!(Objects.equals(studentImage.getOriginalFilename(), ""))) {
            boolean previousStudentImageIsDeleted = this.fileService.deleteImage(path, student.getImage());
            String imageName = this.fileService.uploadImage(path, studentImage);
            student.setImage(imageName);
        }

        student.setStudentId(studentDTO.getStudentId());
        student.setName(studentDTO.getName());
        student.setContact(studentDTO.getContact());
        student.setSession(studentDTO.getSession());
        String verificationCode = RandomString.make(64);
        student.setVerificationCode(verificationCode);
        student.setEnable(true);

//        return this.modelMapper.map(this.studentRepository.save(student), StudentDTO.class);
        student = this.studentRepository.save(student);
        return true;
    }

    // send email for verification
    private void sendVerificationEmail(StudentDTO studentDTO, String siteURL) {
        String subject = "Please, Verify your registration";
        siteURL += "/" + studentDTO.getId() + "/" + studentDTO.getVerificationCode();
        String emailContent = "<p><b>Dear " + studentDTO.getName() + ",</b></p>"
                + "Please click the link below to verify your registration:<br>"
                + "<h1><a href=\"" + siteURL + "\" target=\"_self\">VERIFY</a></h1>"
                + "Thank you,<br>"
                + "ICT, MBSTU.";
        try {
            this.emailSenderService.sendEmailWithoutAttachment(studentDTO.getEmail(), subject, emailContent);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
