package com.example.CodeJuniorBackendEngineer.repository;

import com.example.CodeJuniorBackendEngineer.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
