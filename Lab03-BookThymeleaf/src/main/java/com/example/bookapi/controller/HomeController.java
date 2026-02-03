package com.example.bookapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Dùng @Controller để trả về View (HTML), không dùng @RestController
public class HomeController {

    @GetMapping("/home")
    public String index() {
        // Trả về tên file index (Spring sẽ tự tìm file index.html trong thư mục templates)
        return "index";
    }
}