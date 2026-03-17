package com.db7.kiemtragiuakythu3.enroll;

import com.db7.kiemtragiuakythu3.course.CourseRepository;
import com.db7.kiemtragiuakythu3.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public boolean enroll(Long studentId, Long courseId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            return false;
        }
        var student = studentRepository.findById(studentId).orElseThrow();
        var course = courseRepository.findById(courseId).orElseThrow();

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Enrollment> myEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentIdOrderByEnrolledAtDesc(studentId);
    }
}

