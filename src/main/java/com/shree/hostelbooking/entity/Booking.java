package com.shree.hostelbooking.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    private String user;
    private LocalDateTime bookingTime;
    private LocalDate checkIn;
    private LocalDate checkOut;

    // Getters and Setters
}
