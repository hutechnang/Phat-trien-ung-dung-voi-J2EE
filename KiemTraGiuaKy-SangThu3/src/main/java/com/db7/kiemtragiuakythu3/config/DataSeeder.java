package com.db7.kiemtragiuakythu3.config;

import com.db7.kiemtragiuakythu3.security.Role;
import com.db7.kiemtragiuakythu3.student.Student;
import com.db7.kiemtragiuakythu3.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAdmin(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            if (studentRepository.existsByUsername(adminUsername)) {
                return;
            }

            Student admin = new Student();
            admin.setUsername(adminUsername);
            admin.setEmail("admin@local");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            studentRepository.save(admin);
        };
    }
}

