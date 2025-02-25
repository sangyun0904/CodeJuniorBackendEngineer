package com.example.CodeJuniorBackendEngineer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
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
}
