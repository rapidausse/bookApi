package com.example.bookApi.controller;

import com.example.bookApi.model.dto.BookDTO;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.enums.BookStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.bookApi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping
    public List<Book> getAllBooks(@RequestParam(value = "status", defaultValue = "AVAILABLE") BookStatus status) {
        return bookRepository.findBookByStatus(status);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        System.out.println(">>> Controller executed with id=" + id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Book createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book(bookDTO.getTitle(),bookDTO.getAuthor(),bookDTO.getYear(),bookDTO.getGenre());
        return bookRepository.save(book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setYear(bookDTO.getYear());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setStatus(bookDTO.getStatus());

        return bookRepository.save(existingBook);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        existingBook.setStatus(BookStatus.DELETED);
        bookRepository.save(existingBook);

        return  "Book deleted";
    }
}
