package com.spring.boot.lms.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long id;

    @Column(name = "student_id_it")
    private String studentId;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_email", unique = true)
    private String email;

    @Column(name = "student_contact")
    private String contact;

    @Column(name = "student_session")
    private String session;

    @Column(name = "student_image")
    private String image;

    @Column(name = "student_password")
    private String password;

    @Column(name = "student_is_enable")
    private boolean isEnable;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "student_role", length = 64)
    private String role;

    @OneToMany(mappedBy = "student", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BorrowedBookOfStudent> borrowedBookOfStudents = new ArrayList<>();

}
