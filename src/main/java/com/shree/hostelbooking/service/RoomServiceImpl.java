package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        return (room != null) ? convertToDTO(room) : null;
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {
        Room savedRoom = roomRepository.save(modelMapper.map(roomDTO,Room.class));
        return modelMapper.map(savedRoom, RoomDTO.class);
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        Room updatedRoom = roomRepository.save(room);
        return convertToDTO(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    private RoomDTO convertToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomNumber(room.getRoomNumber());
        roomDTO.setType(room.getType());
        return roomDTO;
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setType(roomDTO.getType());
        return room;
    }

    
}
