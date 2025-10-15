package com.example.bookApi.controller;

import com.example.bookApi.model.dto.ReservationDTO;
import com.example.bookApi.model.entity.Reservation;
import com.example.bookApi.repository.ReservationRepository;
import com.example.bookApi.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private ReservationService reservationService;
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping
    public List<Reservation> getAllReservation(){
        return reservationRepository.findAll();
    }
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));

    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public  Reservation create( @Valid @RequestBody ReservationDTO reservationDTO){
        return  reservationService.create(reservationDTO);
    }
}
