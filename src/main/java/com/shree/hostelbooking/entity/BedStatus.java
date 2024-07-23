package com.shree.hostelbooking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shree.hostelbooking.util.Status;
import com.shree.hostelbooking.util.StatusConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bed_status")
@Data
public class BedStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bedId;

    private Long roomId;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @Convert(converter = StatusConverter.class)
    private Status status;

}
