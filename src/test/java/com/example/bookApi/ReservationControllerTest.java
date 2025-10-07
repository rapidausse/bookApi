package com.example.bookApi;

import com.example.bookApi.controller.ReservationController;
import com.example.bookApi.model.dto.ReservationDTO;
import com.example.bookApi.model.entity.Reservation;
import com.example.bookApi.repository.ReservationRepository;
import com.example.bookApi.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationService reservationService;
    @InjectMocks
    private ReservationController reservationController;

    public ReservationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));
        List<Reservation> result = reservationController.getAllReservation();
        assertEquals(1, result.size());
    }

    @Test
    void testGetReservationFound() {
        UUID id = UUID.randomUUID();
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        Reservation result = reservationController.getReservation(id);
        assertNotNull(result);
    }

    @Test
    void testGetReservationNotFound() {
        UUID id = UUID.randomUUID();
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reservationController.getReservation(id));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testCreateReservation() {
        ReservationDTO dto = new ReservationDTO();
        Reservation reservation = new Reservation();
        when(reservationService.create(dto)).thenReturn(reservation);
        Reservation result = reservationController.create(dto);
        assertNotNull(result);
    }
}
