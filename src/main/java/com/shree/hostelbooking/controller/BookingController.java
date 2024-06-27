package com.shree.hostelbooking.controller;

import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public BookingDTO createBooking(@RequestParam Long roomId,
                                    @RequestParam String user,
                                    @RequestParam  LocalDate checkIn,
                                    @RequestParam LocalDate checkOut) {
        return bookingService.createBooking(roomId, user, checkIn, checkOut);
    }

    @GetMapping("/{id}")
    public BookingDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }
}
