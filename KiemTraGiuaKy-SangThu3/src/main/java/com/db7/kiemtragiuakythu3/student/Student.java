package com.db7.kiemtragiuakythu3.student;

import com.db7.kiemtragiuakythu3.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "students",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_students_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_students_email", columnNames = "email")
        }
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false, length = 120)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.STUDENT;

    @Column(length = 20)
    private String authProvider; // "LOCAL" | "GOOGLE"

    @Column(length = 200)
    private String providerSubject; // OAuth2 "sub"
}

