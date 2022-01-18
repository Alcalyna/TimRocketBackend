package com.example.timrocket_backend.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private UUID id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name= "LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "COMPANY")
    private String company;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    public Member(String firstName, String lastName, String email, String password, String company, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.company = company;
        this.role = role;
    }

    public Member() {
    }

    private enum Role {
        COACH,
        COACHEE,
        ADMIN
    }
}
