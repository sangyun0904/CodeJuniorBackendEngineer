package com.example.CodeJuniorBackendEngineer.controller;

import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedISBN;
import com.example.CodeJuniorBackendEngineer.exceptions.ISBNValidationException;
import com.example.CodeJuniorBackendEngineer.model.Author;
import com.example.CodeJuniorBackendEngineer.model.Book;
import com.example.CodeJuniorBackendEngineer.repository.AuthorRepository;
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
    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping("")
    public ResponseEntity<String> createBook(@RequestBody Book.BookRequestBody requestBody) throws ISBNValidationException, DuplicatedISBN {

        validateBookRequestBody(requestBody);

        Book newBook = new Book(null, requestBody.title(), requestBody.description(), requestBody.isbn(), LocalDate.parse(requestBody.publication_date()), requestBody.author_id());
        return ResponseEntity.ok("id " + bookRepository.save(newBook).getId() + " 도서 생성 완료.");
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) throw new NoSuchElementException(bookId + " id의 도서가 존재하지 않습니다.");

        return ResponseEntity.ok(book.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long bookId, @RequestBody Book.BookRequestBody requestBody) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) throw new NoSuchElementException(bookId + " id의 도서가 존재하지 않습니다.");

        validateBookRequestBody(requestBody);

        Book updatedBook = book.get().updateBook(requestBody.title(), requestBody.description(), requestBody.isbn(), LocalDate.parse(requestBody.publication_date()), requestBody.author_id());
        return ResponseEntity.ok("id " + bookRepository.save(updatedBook).getId() + " 도서 업데이트 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) throw new NoSuchElementException(bookId + " id의 도서가 존재하지 않습니다.");

        bookRepository.delete(book.get());
        return ResponseEntity.ok("id " + book.get().getId() + " 도서 삭제 완료.");
    }

    private void validateBookRequestBody(Book.BookRequestBody requestBody) {

        if (requestBody.title() == null) throw new NullPointerException("도서 제목은 반드시 필요합니다.");
        if (requestBody.isbn() == null) throw new NullPointerException("도서의 ISBN은 반드시 필요합니다.");

        checkISBNValidation(requestBody.isbn());

        Optional<Book> bookByISBN = bookRepository.findByIsbn(requestBody.isbn());
        if (bookByISBN.isPresent()) throw new DuplicatedISBN("해당 ISBN이 이미 존재합니다.");

        Optional<Author> author = authorRepository.findById(requestBody.author_id());
        if (author.isEmpty()) throw new NoSuchElementException(requestBody.author_id() + " id의 저자가 존재하지 않습니다.");

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
