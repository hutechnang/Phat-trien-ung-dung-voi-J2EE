package com.db7.kiemtragiuakythu3.web;

import com.db7.kiemtragiuakythu3.enroll.EnrollmentService;
import com.db7.kiemtragiuakythu3.security.StudentUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EnrollController {
    private final EnrollmentService enrollmentService;

    public EnrollController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll/{courseId}")
    public String enroll(@PathVariable Long courseId,
                         @AuthenticationPrincipal StudentUserDetails principal,
                         RedirectAttributes ra) {
        boolean created = enrollmentService.enroll(principal.getId(), courseId);
        ra.addFlashAttribute("success", created ? "Đăng ký học phần thành công." : "Bạn đã đăng ký học phần này rồi.");
        return "redirect:/home";
    }

    @GetMapping("/enroll/my-courses")
    public String myCourses(@AuthenticationPrincipal StudentUserDetails principal, Model model) {
        model.addAttribute("enrollments", enrollmentService.myEnrollments(principal.getId()));
        return "enroll/my-courses";
    }
}

