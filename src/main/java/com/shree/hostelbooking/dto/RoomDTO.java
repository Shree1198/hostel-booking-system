package com.shree.hostelbooking.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String type;
//    private boolean isAvailable;
   // private List<BedDTO> beds;

}
