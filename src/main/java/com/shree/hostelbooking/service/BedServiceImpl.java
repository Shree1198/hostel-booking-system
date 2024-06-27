package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.repository.BedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BedServiceImpl implements BedService {

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BedDTO> getAvailableBedsByRoom(Long roomId) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);
        if (roomDTO == null) {
            throw new RuntimeException("Room not found");
        }

        Room room = modelMapper.map(roomDTO, Room.class);
        List<Bed> availableBeds = bedRepository.findByRoomAndIsAvailable(room, true);

        return availableBeds.stream()
                .map(bed -> modelMapper.map(bed, BedDTO.class))
                .collect(Collectors.toList());
    }
}
