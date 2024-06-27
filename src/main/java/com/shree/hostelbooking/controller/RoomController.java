package com.shree.hostelbooking.controller;

import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@Slf4j
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public RoomDTO createRoom(@RequestBody RoomDTO roomDTO) {
        log.info("RoomController.createRoom RoomDTO-{}",roomDTO);
        return roomService.saveRoom(roomDTO);
    }

    @PutMapping("/{id}")
    public RoomDTO updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO.setId(id);
        return roomService.updateRoom(roomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
