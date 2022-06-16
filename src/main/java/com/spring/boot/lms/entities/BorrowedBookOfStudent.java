package com.spring.boot.lms.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "borrowed_books_of_students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BorrowedBookOfStudent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id_fk", referencedColumnName = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id_fk", referencedColumnName = "student_id")
    private Student student;

    private boolean isStayingBook;

    private Date bookLiftingDate;

    private Date bookReturningDate;

}
