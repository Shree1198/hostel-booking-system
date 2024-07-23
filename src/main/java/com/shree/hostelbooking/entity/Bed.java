package com.shree.hostelbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bed_room_id", referencedColumnName = "roomId")
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;
}
