package com.db7.kiemtragiuakythu3.enroll;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enrollment> findByStudentIdOrderByEnrolledAtDesc(Long studentId);
}

