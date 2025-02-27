package com.example.CodeJuniorBackendEngineer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false, unique = true)
    private String isbn;
    private LocalDate publication_date;
    @Column(nullable = false)
    private Long author_id;

    public Book updateBook(String title, String description, String isbn, LocalDate publication_date, Long author_id) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publication_date = publication_date;
        this.author_id = author_id;

        return this;
    }

    public record BookRequestBody(String title, String description, String isbn, String publication_date, Long author_id) {}
}
