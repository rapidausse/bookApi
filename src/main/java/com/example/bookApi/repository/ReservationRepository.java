package com.example.bookApi.repository;

import com.example.bookApi.model.entity.Reservation;
import com.example.bookApi.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository  extends JpaRepository<Reservation, UUID>  {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND r.endDate > :startDate " +
            "AND r.startDate < :endDate")
    List<Reservation> findOverlappingReservations(
            @Param("bookId") UUID bookId,
            @Param("startDate") Instant  startDate,
            @Param("endDate") Instant  endDate);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.status = :status " +
            "AND r.startDate < :now")
    List<Reservation> findNotActiveBeforeNow(
            @Param("status") ReservationStatus status,
            @Param("now") Instant  now);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.status = :status " +
            "AND r.endDate < :now")
    List<Reservation> findActiveAfterNow(
            @Param("status") ReservationStatus status,
            @Param("now") Instant now);



}
