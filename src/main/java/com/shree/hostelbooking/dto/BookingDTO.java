package com.shree.hostelbooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "provide valid check-in date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkIn;
    // if not given then consider one day booking
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkOut;

    @AssertTrue(message = "Check-out date must be after check-in date")
    public boolean isCheckOutAfterCheckIn() {
        if (checkOut == null) {
            return true; // Handle null checks separately if needed
        }
        return checkOut.isAfter(checkIn);
    }
}
