package com.example.bookApi.repository;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findBookByStatus(BookStatus status);
}

