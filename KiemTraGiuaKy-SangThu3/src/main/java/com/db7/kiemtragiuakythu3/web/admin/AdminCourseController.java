package com.db7.kiemtragiuakythu3.web.admin;

import com.db7.kiemtragiuakythu3.course.Course;
import com.db7.kiemtragiuakythu3.course.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {
    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("coursePage", courseService.listPaged(page, 10, null));
        return "admin/courses/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/courses/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Course course, RedirectAttributes ra) {
        courseService.save(course);
        ra.addFlashAttribute("success", "Tạo học phần thành công.");
        return "redirect:/admin/courses";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "admin/courses/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Course form, RedirectAttributes ra) {
        Course c = courseService.getById(id);
        c.setName(form.getName());
        c.setCredits(form.getCredits());
        c.setInstructor(form.getInstructor());
        c.setImageUrl(form.getImageUrl());
        courseService.save(c);
        ra.addFlashAttribute("success", "Cập nhật học phần thành công.");
        return "redirect:/admin/courses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        courseService.delete(id);
        ra.addFlashAttribute("success", "Xóa học phần thành công.");
        return "redirect:/admin/courses";
    }
}

