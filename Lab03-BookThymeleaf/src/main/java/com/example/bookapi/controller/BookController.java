package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books"; // Trả về file books.html trong thư mục templates
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id); // Gọi service để xóa sách theo ID
        return "redirect:/books"; // Chuyển hướng người dùng quay lại danh sách sách
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable int id, Model model) {
        // Tìm sách theo ID, nếu thấy thì đưa vào model để hiển thị lên form
        bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
        return "edit-book"; // Trả về file edit-book.html
    }

    // 2. Xử lý cập nhật sách
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book); // Gọi service để cập nhật thông tin
        return "redirect:/books"; // Quay lại trang danh sách
    }
}