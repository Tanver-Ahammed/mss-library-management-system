package com.spring.boot.lms.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "librarians")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Librarian implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "librarian_id")
    private long id;

    @Column(name = "librarian_name")
    private String name;

    @Column(name = "librarian_email", unique = true)
    private String email;

    @Column(name = "librarian_contact")
    private String contact;

    @Column(name = "librarian_image")
    private String image;

    @Column(name = "librarian_password")
    private String password;

    @Column(name = "librarian_is_enable")
    private boolean isEnable;

    @Column(name = "librarian_role")
    private String role;


}
