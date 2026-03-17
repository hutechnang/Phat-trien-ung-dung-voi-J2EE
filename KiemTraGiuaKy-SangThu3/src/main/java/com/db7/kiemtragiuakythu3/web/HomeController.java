package com.db7.kiemtragiuakythu3.web;

import com.db7.kiemtragiuakythu3.course.CourseService;
import com.db7.kiemtragiuakythu3.security.StudentUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping({"/", "/home", "/courses"})
    public String home(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String q,
            Model model,
            @AuthenticationPrincipal Object principal
    ) {
        var coursePage = courseService.listPaged(page, 5, q);
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("q", q);

        // Used in navbar to decide showing "My Courses" link even for OAuth2 user
        boolean isStudent = false;
        if (principal instanceof StudentUserDetails sud) {
            isStudent = sud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        } else if (principal instanceof OAuth2User oauth2User) {
            isStudent = oauth2User.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        }
        model.addAttribute("isStudent", isStudent);
        return "home";
    }
}

