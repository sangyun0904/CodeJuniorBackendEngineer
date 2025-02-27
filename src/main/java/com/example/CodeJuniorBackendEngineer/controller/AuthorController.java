package com.example.CodeJuniorBackendEngineer.controller;

import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedEmail;
import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedISBN;
import com.example.CodeJuniorBackendEngineer.model.Author;
import com.example.CodeJuniorBackendEngineer.model.Book;
import com.example.CodeJuniorBackendEngineer.repository.AuthorRepository;
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

    @PostMapping("")
    public ResponseEntity<Long> createAuthor(@RequestBody Author.AuthorRequestBody request) throws DuplicatedEmail {

        Optional<Author> authorByEmail = authorRepository.findByEmail(request.email());
        if (authorByEmail.isPresent()) throw new DuplicatedEmail("This Email already Exists!!!");

        Author newAuthor = new Author(null, request.name(), request.email());
        return ResponseEntity.ok(authorRepository.save(newAuthor).getId());
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthor() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) throw new NoSuchElementException("Author by id : " + id + " doesn't Exists!!!");

        return ResponseEntity.ok(author.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAuthor(@PathVariable("id") Long id, @RequestBody Author.AuthorRequestBody request) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) throw new NoSuchElementException("Author by id : " + id + " doesn't Exists!!!");

        Author updatedAuthor = author.get().updateAuthorInfo(request.name(), request.email());
        return ResponseEntity.ok(authorRepository.save(updatedAuthor).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAuthor(@PathVariable("id") Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) throw new NoSuchElementException("Author by id : " + id + " doesn't Exists!!!");

        authorRepository.delete(author.get());
        return ResponseEntity.ok(author.get().getId());
    }
}
