package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import org.springframework.web.client.ResourceAccessException;

import java.lang.module.ResolutionException;
import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();

    RoomDTO getRoomById(Long id) throws ResourceNotFoundException;

    RoomDTO saveRoom(RoomDTO roomDTO);

    RoomDTO updateRoom(RoomDTO roomDTO);

    void deleteRoom(Long id) throws ResourceNotFoundException;

    List<BedDTO> getAvailableBedsByRoom(RoomDTO roomDTO);
}
