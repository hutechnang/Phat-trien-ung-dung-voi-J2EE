package com.db7.kiemtragiuakythu3.security;

import com.db7.kiemtragiuakythu3.student.Student;
import com.db7.kiemtragiuakythu3.student.StudentRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2StudentService extends DefaultOAuth2UserService {
    private final StudentRepository studentRepository;

    public OAuth2StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String sub = (String) attributes.get("sub");
        String name = (String) attributes.getOrDefault("name", email);

        // Ensure student exists (default role STUDENT)
        Student student = studentRepository.findByEmail(email).orElseGet(() -> {
            Student s = new Student();
            s.setEmail(email);
            s.setUsername(makeUsernameFromEmail(email, name));
            s.setPassword(null);
            s.setRole(Role.STUDENT);
            s.setAuthProvider("GOOGLE");
            s.setProviderSubject(sub);
            return studentRepository.save(s);
        });

        // If previously local, keep role; but mark provider if missing
        if (student.getAuthProvider() == null) {
            student.setAuthProvider("GOOGLE");
            student.setProviderSubject(sub);
            studentRepository.save(student);
        }

        var authorities = new StudentUserDetails(student).getAuthorities();
        return new DefaultOAuth2User(authorities, attributes, "email");
    }

    private String makeUsernameFromEmail(String email, String name) {
        String base = email != null && email.contains("@") ? email.substring(0, email.indexOf('@')) : "student";
        base = base.replaceAll("[^a-zA-Z0-9._-]", "");
        if (base.isBlank()) base = "student";
        String candidate = base;
        int i = 1;
        while (studentRepository.existsByUsername(candidate)) {
            candidate = base + i;
            i++;
        }
        return candidate;
    }
}

