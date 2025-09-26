package com.example.bookApi.controller;

import com.example.bookApi.model.dto.ReservationDTO;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.entity.Reservation;
import com.example.bookApi.model.entity.User;
import com.example.bookApi.model.enums.BookStatus;
import com.example.bookApi.repository.BookRepository;
import com.example.bookApi.repository.ReservationRepository;
import com.example.bookApi.repository.UserRepository;
import com.example.bookApi.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER)")
    @GetMapping
    public List<Reservation> getAllReservation(){
        return reservationRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN)")
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }



    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping
    public  Reservation create(@RequestBody ReservationDTO reservationDTO){
        return  reservationService.create(reservationDTO);
    }





}
