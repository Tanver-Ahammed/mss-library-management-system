package com.spring.boot.lms.controller;

import com.spring.boot.lms.dto.StudentDTO;
import com.spring.boot.lms.service.impl.FileServiceImpl;
import com.spring.boot.lms.service.impl.StudentServiceImpl;
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
@RequestMapping(path = "/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    @GetMapping(path = "/add")
    public String addStudent(Model model) {
        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("message", "");
        return "add-student";
    }

    @PostMapping(path = "/save")
    public String saveStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult result,
                              @RequestParam(value = "studentImage", required = false) MultipartFile studentImage,
                              Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("studentDTO", studentDTO);
            return "add-student";
        }
        studentDTO = this.studentService.addStudent(studentDTO, studentImage);
        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("message", "You have Registered Successfully...\n" +
                "Please, Check your Email and verify your account");
        return "add-student";
    }

    @GetMapping(path = "/verify/{id}/{verifyCode}")
    public String verifyStudent(@PathVariable("id") Integer id,
                                @PathVariable("verifyCode") String verifyCode,
                                Model model) {
        boolean isMatch = this.studentService.verifyStudent(id, verifyCode);
        if (isMatch)
            model.addAttribute("message", "Your email verifying successfully....");
        else
            model.addAttribute("message", "Your email don't verifying successfully....");
        return "home";
    }

    // get all students
    @GetMapping(path = "/all")
    public String getAllStudents(Model model) {
        List<StudentDTO> studentDTOS = this.studentService.getAllStudents();
        model.addAttribute("studentDTOS", studentDTOS);
        model.addAttribute("message", "");
        return "show-all-students";
    }

    // get single book
    @GetMapping(path = "/get/{studentId}")
    public String getSingleStudent(@PathVariable("studentId") long studentId, Model model) {
        StudentDTO studentDTO = this.studentService.getStudentById(studentId);
        model.addAttribute("studentDTO", studentDTO);
        return "show-single-student";
    }

    // edit student details
    @GetMapping(path = "/edit/{studentId}")
    public String editStudent(@PathVariable("studentId") long studentId, Model model) {
        StudentDTO studentDTO = this.studentService.getStudentById(studentId);
        model.addAttribute("studentDTO", studentDTO);
        model.addAttribute("message", "");
        return "update-student";
    }

    // update student
    @PostMapping(path = "/update/{studentId}")
    public String updateStudentSuccess(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult result,
                                       @RequestParam("studentImage") MultipartFile studentImage,
                                       @PathVariable("studentId") long studentId,
                                       Model model) throws IOException {
        System.err.println(studentDTO);
        if (result.hasErrors()) {
            model.addAttribute("studentDTO", studentDTO);
            return "update-student";
        }
        boolean isStudentUpdated = this.studentService.updateStudent(studentDTO, studentImage, studentId);
        if (!isStudentUpdated) {
            model.addAttribute("message", "Your Password is wrong!!!");
            return "update-student";
        }
        model.addAttribute("message", "studentDTO.getName()" + ", You are successfully updated...");
        return "redirect:/student/all";
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
