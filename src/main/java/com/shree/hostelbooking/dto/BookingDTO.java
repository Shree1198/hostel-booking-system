package com.shree.hostelbooking.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private BedDTO bed;
    private String user;

    //to be used for audit and availability cal.
    private LocalDateTime bookingDateTime = LocalDateTime.now();

    // to make bed as unavailable check-in date considered
    private LocalDate checkIn;
    // if not given then consider one day booking
    private LocalDate checkOut;

    @AssertTrue(message = "Check-out date must be after check-in date")
    public boolean isCheckOutAfterCheckIn() {
        if (checkIn == null || checkOut == null) {
            return true; // Handle null checks separately if needed
        }
        return checkOut.isAfter(checkIn);
    }
}
