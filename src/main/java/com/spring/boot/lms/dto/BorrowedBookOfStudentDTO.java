package com.spring.boot.lms.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BorrowedBookOfStudentDTO {

    private long id;

    private BookDTO bookDTO;

    private StudentDTO studentDTO;

    private boolean isStayingBook;

    private Date bookLiftingDate;

    private Date bookReturningDate;

}
