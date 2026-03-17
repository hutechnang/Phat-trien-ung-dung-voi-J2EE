package com.db7.kiemtragiuakythu3.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public Page<Course> listPaged(int page, int size, String q) {
        var pageable = PageRequest.of(Math.max(page, 0), size, Sort.by("id").descending());
        if (q == null || q.isBlank()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByNameContainingIgnoreCase(q.trim(), pageable);
    }

    @Transactional(readOnly = true)
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}

