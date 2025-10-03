package com.example.bookApi.model.entity;

import com.example.bookApi.model.enums.BookStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;
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

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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



