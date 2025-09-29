package com.example.bookApi.service;

import com.example.bookApi.model.dto.ReservationDTO;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.entity.Reservation;
import com.example.bookApi.model.entity.User;
import com.example.bookApi.model.enums.BookStatus;
import com.example.bookApi.model.enums.ReservationStatus;
import com.example.bookApi.repository.BookRepository;
import com.example.bookApi.repository.ReservationRepository;
import com.example.bookApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(ReservationDTO reservationDTO) {
        Instant startDateUtc = reservationDTO.getStartDate().toInstant();
        Instant endDateUtc = reservationDTO.getEndDate().toInstant();
        Instant now = Instant.now();

        System.out.println("ZonedDateTime (user) : " +  reservationDTO.getStartDate());
        System.out.println("Instant (UTC) : " + startDateUtc);

        if (endDateUtc.isBefore(startDateUtc)) {
            throw new IllegalArgumentException("endDate is after startDate");
        }

        if (startDateUtc.isBefore(now)) {
            throw new IllegalArgumentException(
                    String.format("Invalid startDate: %s. It must be after current date %s",
                            reservationDTO.getStartDate(), now)
            );
        }



        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(reservationDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("The book is not available for a reservation");
        }



        List<Reservation> reservations = reservationRepository.findOverlappingReservations(
                book.getId(),
                startDateUtc,
                endDateUtc
        );

        if (!reservations.isEmpty()) {
            throw new IllegalStateException("The book is already reserved for the selected period");
        }

        Reservation reservation = new Reservation(book,user,startDateUtc,endDateUtc, ReservationStatus.NOT_ACTIVE);
        return  reservationRepository.save(reservation);
    }


    @Scheduled(cron = "0 * * * * *")
    public void activatePendingReservations() {
        Instant now = Instant.now();
        List<Reservation> reservations = reservationRepository.findNotActiveBeforeNow(ReservationStatus.NOT_ACTIVE, now);

        for (Reservation reservation : reservations) {
            Book book = reservation.getBook();
            book.setStatus(BookStatus.NOT_AVAILABLE);
            reservation.setStatus(ReservationStatus.ACTIVE);
        }
    }


    @Scheduled(cron = "0 * * * * *")
    public void expireActiveReservations() {
        Instant now = Instant.now();
        List<Reservation> reservations = reservationRepository.findActiveAfterNow(ReservationStatus.ACTIVE, now);

        for (Reservation reservation : reservations) {
            Book book = reservation.getBook();
            book.setStatus(BookStatus.AVAILABLE);
            reservation.setStatus(ReservationStatus.DELETED);
        }
    }


}
