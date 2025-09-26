package com.example.bookApi.service;

import com.example.bookApi.model.dto.ReservationDTO;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.entity.Reservation;
import com.example.bookApi.model.entity.User;
import com.example.bookApi.model.enums.BookStatus;
import com.example.bookApi.repository.BookRepository;
import com.example.bookApi.repository.ReservationRepository;
import com.example.bookApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;


    public Reservation create(ReservationDTO reservationDTO) {
        if (reservationDTO.getEndDate().isBefore(reservationDTO.getStartDate()) || reservationDTO.getEndDate().isEqual(reservationDTO.getStartDate())) {
            throw new IllegalArgumentException("endDate is after startDate");
        }

        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(reservationDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("The book is not available for a reservation");
        }
        book.setStatus(BookStatus.NOT_AVAILABLE);

        Reservation reservation = new Reservation(book,user,reservationDTO.getStartDate(),reservationDTO.getEndDate());
        return  reservationRepository.save(reservation);
    }


    @Scheduled(cron = "0 0 * * * *")
    public void updateBookStatusAfterReservationEnd() {    }


}
