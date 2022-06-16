package com.spring.boot.lms.dto;


import com.spring.boot.lms.entities.BorrowedBookOfStudent;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {

    private long id;

    @NotBlank
    @Size(min = 6, max = 7, message = "student's id must be min of 6 to 7 character")
    private String studentId;

    @NotBlank
    @Size(min = 3, max = 100, message = "student's name must be min of 3 to 100 character")
    private String name;

    @NotBlank
    @Email(message = "your email address is not valid")
    private String email;

    @NotBlank
    private String contact;

    @NotBlank
    private String session;

//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}",
//            message = "your password has 8-20 character and al least 1 digit, 1 lowercase," +
//                    " 1 uppercase, 1 [@#$%] special character")
    @NotBlank
    private String password;

    private String image;

    private boolean isEnable;

    private String verificationCode;

    private String role;

    private List<BorrowedBookOfStudent> borrowedBookOfStudents = new ArrayList<>();

}
