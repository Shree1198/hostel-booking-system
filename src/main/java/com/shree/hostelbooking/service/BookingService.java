package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookingService {
    BookingDTO createBooking(Long roomId, String user, LocalDate checkIn, LocalDate checkOut);

    BookingDTO getBookingById(Long id) throws ResourceNotFoundException;

    String cancelBooking(Long bookingId);
}
