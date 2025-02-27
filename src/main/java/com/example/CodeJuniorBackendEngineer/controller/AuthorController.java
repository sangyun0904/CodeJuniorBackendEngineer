package com.example.CodeJuniorBackendEngineer.controller;

import com.example.CodeJuniorBackendEngineer.exceptions.DeleteBookBeforeAuthorException;
import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedEmail;
import com.example.CodeJuniorBackendEngineer.model.Author;
import com.example.CodeJuniorBackendEngineer.model.Book;
import com.example.CodeJuniorBackendEngineer.repository.AuthorRepository;
import com.example.CodeJuniorBackendEngineer.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    @PostMapping("")
    public ResponseEntity<Long> createAuthor(@RequestBody Author.AuthorRequestBody request) throws DuplicatedEmail {

        Optional<Author> authorByEmail = authorRepository.findByEmail(request.email());
        if (authorByEmail.isPresent()) throw new DuplicatedEmail("해당 Email이 이미 존재합니다.");

        Author newAuthor = new Author(null, request.name(), request.email());
        return ResponseEntity.ok(authorRepository.save(newAuthor).getId());
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthor() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) throw new NoSuchElementException(authorId + " id의 저자가 존재하지 않습니다.");

        return ResponseEntity.ok(author.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAuthor(@PathVariable("id") Long authorId, @RequestBody Author.AuthorRequestBody request) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) throw new NoSuchElementException(authorId + " id의 저자가 존재하지 않습니다.");

        Author updatedAuthor = author.get().updateAuthorInfo(request.name(), request.email());
        return ResponseEntity.ok(authorRepository.save(updatedAuthor).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAuthor(@PathVariable("id") Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) throw new NoSuchElementException(authorId + " id의 저자가 존재하지 않습니다.");

        List<Book> bookByAuthorList = bookRepository.findAllByAuthorId(authorId);
        if (!bookByAuthorList.isEmpty()) throw new DeleteBookBeforeAuthorException("이 저자의 도서가 존재합니다. 도서를 먼저 삭제해주세요.");

        authorRepository.delete(author.get());
        return ResponseEntity.ok(author.get().getId());
    }
}
