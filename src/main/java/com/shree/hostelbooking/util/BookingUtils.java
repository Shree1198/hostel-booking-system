package com.shree.hostelbooking.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingUtils {

    public static LocalDateTime getDefaultCheckOut(LocalDateTime checkIn) {
        if (checkIn == null) {
            throw new RuntimeException("Check-in date cannot be null");
        }
        // Default checkOut is one day after checkIn, ending at 11:00 AM
        return checkIn.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
    }
}
