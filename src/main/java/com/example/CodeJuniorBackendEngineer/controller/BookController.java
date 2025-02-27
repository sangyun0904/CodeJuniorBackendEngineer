package com.example.CodeJuniorBackendEngineer.controller;

import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedISBN;
import com.example.CodeJuniorBackendEngineer.exceptions.ISBNValidationException;
import com.example.CodeJuniorBackendEngineer.model.Author;
import com.example.CodeJuniorBackendEngineer.model.Book;
import com.example.CodeJuniorBackendEngineer.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("")
    public ResponseEntity<Long> createBook(@RequestBody Book.BookRequestBody request) throws ISBNValidationException, DuplicatedISBN {

        checkISBNValidation(request.isbn());

        Optional<Book> bookByISBN = bookRepository.findByIsbn(request.isbn());
        if (bookByISBN.isPresent()) throw new DuplicatedISBN("This ISBN already Exists!!!");

        Book newBook = new Book(null, request.title(), request.description(), request.isbn(), LocalDate.parse(request.publication_date()), request.author_id());
        return ResponseEntity.ok(bookRepository.save(newBook).getId());
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) throw new NoSuchElementException("Book by id : " + bookId + " doesn't Exists!!!");

        return ResponseEntity.ok(book.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBook(@PathVariable("id") Long bookId, @RequestBody Book.BookRequestBody request) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) throw new NoSuchElementException("Book by id : " + bookId + " doesn't Exists!!!");

        Book updatedBook = book.get().updateBook(request.title(), request.description(), request.isbn(), LocalDate.parse(request.publication_date()), request.author_id());
        return ResponseEntity.ok(bookRepository.save(updatedBook).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable("id") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) throw new NoSuchElementException("Book by id : " + bookId + " doesn't Exists!!!");

        bookRepository.delete(book.get());
        return ResponseEntity.ok(book.get().getId());
    }

    private void checkISBNValidation(String isbn) {
        // 전부 숫자인지 체크
        try {
            Long.parseLong(isbn);
        } catch (Exception e) {
            throw new ISBNValidationException("ISBN은 전부 숫자로 이루어져 있어야 합니다.");
        }

        // 10자리인지 체크
        if (isbn.length() != 10) throw new ISBNValidationException("ISBN은 10자리 숫자여야 합니다.");

        // 국가, 언어 식별 번호 체크
        int nationLanguageCode = Integer.parseInt(isbn.substring(0,2));
        if (nationLanguageCode < 10 || nationLanguageCode > 90) throw new ISBNValidationException("ISBN의 국가, 언어 식별 번호(첫 번째 두 자리)는 10~90 사이의 숫자만 허용합니다.");

        // 마지막 0 체크
        if (!isbn.substring(9).equals("0")) throw new ISBNValidationException("ISBN의 마지막은 숫자 0이어야 합니다.");

    }

}
