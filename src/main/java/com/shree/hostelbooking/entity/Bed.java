package com.shree.hostelbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isAvailable;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bed_room_id", referencedColumnName = "roomId")
    private Room room;

    // Getters and Setters
}
