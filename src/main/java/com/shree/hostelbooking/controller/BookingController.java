package com.shree.hostelbooking.controller;

import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@Validated
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //    @PostMapping("/create")
//    public BookingDTO createBooking(@RequestParam Long roomId,
//                                    @RequestParam String user,
//                                    @RequestParam LocalDate checkIn,
//                                    @RequestParam LocalDate checkOut) {
//        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
//            return ResponseEntity.badRequest().body("Check-out date must be after check-in date and both dates must be provided");
//        return bookingService.createBooking(roomId, user, checkIn, checkOut);
//    }
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam Long roomId,
                                           @RequestParam String user,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime checkIn,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            return ResponseEntity.badRequest().body("Check-out date must be after check-in date and both dates must be provided");
        }
        BookingDTO createdBooking = bookingService.createBooking(roomId, user, checkIn, checkOut);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }


    @GetMapping
    public List<BookingDTO> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK).getBody();
    }

    @GetMapping("/{id}")
    public BookingDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        String result = bookingService.cancelBooking(id);
        return ResponseEntity.ok(result);
    }
}
