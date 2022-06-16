package com.spring.boot.lms.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LibrarianDTO {

    private long id;

    @NotBlank
    @Size(min = 3, max = 100, message = "librarian's name must be min of 3 to 100 character")
    private String name;

    @NotBlank
    @Email(message = "your email address is not valid")
    private String email;

    @NotBlank(message = "please enter your valid contact number")
    private String contact;

    @NotBlank(message = "please enter your valid password number")
    private String password;

    private String image;

    private boolean isEnable;

    private String role;

}
