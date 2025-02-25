package com.example.CodeJuniorBackendEngineer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    public Author updateAuthorInfo(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

    public record AuthorRequestBody(String name, String email) {}
}
