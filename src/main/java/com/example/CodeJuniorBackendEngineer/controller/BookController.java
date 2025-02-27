package com.example.CodeJuniorBackendEngineer.controller;

import com.example.CodeJuniorBackendEngineer.model.Book;
import com.example.CodeJuniorBackendEngineer.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("")
    public ResponseEntity<Long> createBook(@RequestBody Book.BookRequestBody request) {
        Book newBook = new Book(null, request.title(), request.description(), request.isbn(), LocalDate.parse(request.publication_date()), request.author_id());
        return ResponseEntity.ok(bookRepository.save(newBook).getId());
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
        return ResponseEntity.ok(bookRepository.findById(bookId).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBook(@PathVariable("id") Long bookId, @RequestBody Book.BookRequestBody request) {
        Book book = bookRepository.findById(bookId).get();
        Book updatedBook = book.updateBook(request.title(), request.description(), request.isbn(), LocalDate.parse(request.publication_date()), request.author_id());
        return ResponseEntity.ok(bookRepository.save(updatedBook).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable("id") Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        bookRepository.delete(book);
        return ResponseEntity.ok(book.getId());
    }

}
