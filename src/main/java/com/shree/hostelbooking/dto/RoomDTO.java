package com.shree.hostelbooking.dto;

import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String type;
    private boolean isAvailable;

    // Getters and Setters
}
