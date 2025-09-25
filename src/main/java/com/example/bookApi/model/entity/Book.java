package com.example.bookApi.model.entity;

import com.example.bookApi.model.enums.BookStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private int year;
    private String genre;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    public Book( String title, String author, int year, String genre, BookStatus status) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.status = status;
    }

    public Book( String title, String author, int year, String genre) {
        this(title, author, year, genre, BookStatus.NOT_AVAILABLE);
    }


    public Book() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}



