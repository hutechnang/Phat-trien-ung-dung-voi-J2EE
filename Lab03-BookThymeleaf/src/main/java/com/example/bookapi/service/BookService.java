package com.example.bookapi.service;

import com.example.bookapi.model.Book; // Khai báo lớp Book từ package model
import org.springframework.stereotype.Service; // Khai báo annotation @Service
import java.util.ArrayList; // Thư viện cho danh sách mảng
import java.util.List; // Thư viện cho kiểu List
import java.util.Optional; // Thư viện cho kiểu Optional
@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L; // Bộ đếm ID tự tăng

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setId(Math.toIntExact(nextId++)); // Gán ID rồi tăng lên
        books.add(book);
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }

    public void updateBook(Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == updatedBook.getId())
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                }); //
    }

    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id); //
    }
}