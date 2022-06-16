package com.spring.boot.lms.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "book_author")
    private String author;

    @Column(name = "total_books")
    private Long total;

    @Column(name = "stock_books")
    private Long stock;

    @Column(name = "book_image")
    private String image;

    @Column(name = "book_is_enable")
    private boolean isEnable;

    @OneToMany(mappedBy = "book", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BorrowedBookOfStudent> borrowedBookOfStudents = new ArrayList<>();

}
