package com.shree.hostelbooking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private Long roomId;
    private String user;

    //to be used for audit and availability cal.
    private LocalDateTime bookingDateTime = LocalDateTime.now();

    // to make bed as unavailable check-in date considered
    private LocalDate checkIn;
    // if not given then consider one day booking
    private LocalDate checkOut;

    // Getters and Setters
}
