package com.db7.kiemtragiuakythu3.student;

import com.db7.kiemtragiuakythu3.security.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Student registerLocal(String username, String rawPassword, String email) {
        if (studentRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (studentRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Student s = new Student();
        s.setUsername(username);
        s.setEmail(email);
        s.setPassword(passwordEncoder.encode(rawPassword));
        s.setRole(Role.STUDENT);
        s.setAuthProvider("LOCAL");
        return studentRepository.save(s);
    }
}

