package com.example.bookApi.model.dto;

import com.example.bookApi.model.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookDTO {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @NotNull(message = "Year is required")
    private int year;
    @NotBlank(message = "Genre is required")
    private String genre;
    private BookStatus status;

    public BookDTO(String title, String author, int year, String genre) {
        this(title,author,year,genre, BookStatus.NOT_AVAILABLE);
    }

    public BookDTO(String title, String author, int year, String genre, BookStatus status) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.status = status;
    }

    public BookDTO(){}

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
