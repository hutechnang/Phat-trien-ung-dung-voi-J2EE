package com.db7.kiemtragiuakythu3.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false, length = 120)
    private String instructor;

    @Column(length = 500)
    private String imageUrl;
}

