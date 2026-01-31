package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final List<Book> bookList = new ArrayList<>();

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookList);
    }

    public Book getBookById(int id) {
        Optional<Book> result = bookList.stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        return result.orElse(null);
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void updateBook(int id, Book newBook) {
        for (Book b : bookList) {
            if (b.getId() == id) {
                b.setTitle(newBook.getTitle());
                b.setAuthor(newBook.getAuthor());
                break;
            }
        }
    }

    public void deleteBook(int id) {
        bookList.removeIf(b -> b.getId() == id);
    }
}
