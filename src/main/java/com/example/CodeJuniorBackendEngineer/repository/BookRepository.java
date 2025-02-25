package com.example.CodeJuniorBackendEngineer.repository;

import com.example.CodeJuniorBackendEngineer.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
