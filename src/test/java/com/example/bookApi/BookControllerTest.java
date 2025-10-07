package com.example.bookApi;

import com.example.bookApi.controller.BookController;
import com.example.bookApi.model.dto.BookDTO;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.enums.BookStatus;
import com.example.bookApi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    public BookControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book = new Book("Title", "Author", 2020, "Genre");
        book.setStatus(BookStatus.AVAILABLE);
        when(bookRepository.findBookByStatus(BookStatus.AVAILABLE)).thenReturn(Arrays.asList(book));
        List<Book> books = bookController.getAllBooks(BookStatus.AVAILABLE);
        assertEquals(1, books.size());
        assertEquals("Title", books.get(0).getTitle());
    }

    @Test
    void testGetBookFound() {
        UUID id = UUID.randomUUID();
        Book book = new Book("Title", "Author", 2020, "Genre");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Book result = bookController.getBook(id);
        assertEquals("Title", result.getTitle());
    }

    @Test
    void testGetBookNotFound() {
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bookController.getBook(id));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testCreateBook() {
        BookDTO dto = new BookDTO("Title", "Author", 2020, "Genre", BookStatus.AVAILABLE);
        Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getYear(), dto.getGenre());
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book result = bookController.createBook(dto);
        assertEquals("Title", result.getTitle());
    }

    @Test
    void testUpdateBookFound() {
        UUID id = UUID.randomUUID();
        BookDTO dto = new BookDTO("NewTitle", "NewAuthor", 2021, "NewGenre", BookStatus.AVAILABLE);
        Book book = new Book("OldTitle", "OldAuthor", 2020, "OldGenre");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book result = bookController.updateBook(id, dto);
        assertEquals("NewTitle", result.getTitle());
    }

    @Test
    void testUpdateBookNotFound() {
        UUID id = UUID.randomUUID();
        BookDTO dto = new BookDTO("Title", "Author", 2020, "Genre", BookStatus.AVAILABLE);
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bookController.updateBook(id, dto));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testDeleteBookFound() {
        UUID id = UUID.randomUUID();
        Book book = new Book("Title", "Author", 2020, "Genre");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        String result = bookController.deleteBook(id);
        assertEquals("Book deleted", result);
        assertEquals(BookStatus.DELETED, book.getStatus());
    }

    @Test
    void testDeleteBookNotFound() {
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bookController.deleteBook(id));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
