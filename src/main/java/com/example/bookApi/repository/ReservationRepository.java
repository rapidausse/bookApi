package com.example.bookApi.repository;

import com.example.bookApi.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation, Long>  {
}
