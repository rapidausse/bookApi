package com.example.bookApi.model.entity;

import com.example.bookApi.model.enums.ReservationStatus;
import jakarta.persistence.*;

import java.time.Instant;


@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Instant startDate;

    private Instant endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation(Book book, User user, Instant  startDate, Instant  endDate, ReservationStatus status) {
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Reservation() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant  getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant  startDate) {
        this.startDate = startDate;
    }

    public Instant  getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant  endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
