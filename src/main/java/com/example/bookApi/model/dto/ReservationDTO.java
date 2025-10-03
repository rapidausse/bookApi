package com.example.bookApi.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ReservationDTO {
    @NotNull(message = "userId is required")
    private UUID userId;
    @NotNull(message = "bookId is required")
    private UUID bookId;
    @NotNull(message = "startDate is required")
    private ZonedDateTime startDate;
    @NotNull(message = "endDate is required")
    private ZonedDateTime endDate;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
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
