package com.db7.kiemtragiuakythu3.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

