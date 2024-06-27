package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();
    RoomDTO getRoomById(Long id);
    RoomDTO saveRoom(RoomDTO roomDTO);
    RoomDTO updateRoom(RoomDTO roomDTO);
    void deleteRoom(Long id);
}
