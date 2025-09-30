package com.example.bookApi.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.ZonedDateTime;

public class ReservationDTO {
    @NotBlank(message = "userId is required")
    private Long userId;
    @NotBlank(message = "bookId is required")
    private Long bookId;
    @NotBlank(message = "startDate is required")
    private ZonedDateTime startDate;
    @NotBlank(message = "endDate is required")
    private ZonedDateTime endDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }
}
