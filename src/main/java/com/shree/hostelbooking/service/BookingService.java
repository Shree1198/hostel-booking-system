package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(Long roomId, String user, LocalDate checkIn, LocalDate checkOut);

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(Long id) throws ResourceNotFoundException;

    @Transactional
    String cancelBooking(Long bookingId) throws ResourceNotFoundException;

//    String cancelBooking(Long bookingId);
}
