package com.spring.boot.lms.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {

    private long id;

    @NotBlank
    @Size(min = 1, max = 100, message = "book's name must be min of 1 to 100 character")
    private String name;

    @NotBlank
    @Size(min = 3, max = 100, message = "book's author name must be min of 3 to 100 character")
    private String author;

    @NotNull
    private long total;

    private long stock;

    private String image;

    private boolean isEnable;

    private List<BorrowedBookOfStudentDTO> borrowedBookOfStudentDTOS = new ArrayList<>();

}
