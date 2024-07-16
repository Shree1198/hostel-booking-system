package com.shree.hostelbooking.dto;

import lombok.Data;

@Data
public class BedDTO {
    private Long id;
    private boolean isAvailable;
    private RoomDTO roomDTO;
    // Getters and Setters
}
