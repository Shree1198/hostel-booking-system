package com.shree.hostelbooking.dto;

import lombok.Data;

@Data
public class BedDTO {
    private Long id;
    private boolean availability;
    private RoomDTO room;
    // Getters and Setters
}
