package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import com.shree.hostelbooking.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j

public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    BedService bedService;

    @Autowired
    private final ModelMapper modelMapper;

    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        return (room != null) ? modelMapper.map(room, RoomDTO.class) : null;
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {

        Room savedRoom = null ;
        List<BedDTO> saveBeds = null;

        List< BedDTO> bedsToSave = roomDTO.getBeds();

        Room room = new Room();
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setType(roomDTO.getType());

        List<Bed> beds = new ArrayList<>();

        if(bedsToSave != null && !bedsToSave.isEmpty()){
            for (BedDTO bedDTO : bedsToSave){
                Bed bed = new Bed();
                bed.setRoom(room);
                bed.setAvailable(true);
                beds.add(bed);
            }
            saveBeds = bedService.saveBeds(beds);
            log.info("saved beds -{}",saveBeds);
            savedRoom = roomRepository.findByRoomNumber(roomDTO.getRoomNumber());
        }else{
             savedRoom = roomRepository.save(modelMapper.map(roomDTO, Room.class));
        }


        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber(savedRoom.getRoomNumber());
        dto.setType(savedRoom.getType());
        dto.setId(savedRoom.getRoomId());
        dto.setBeds(saveBeds);


        return dto;
    }

    @Override
    public List<BedDTO> getAvailableBedsByRoom(RoomDTO roomDTO) throws ResourceNotFoundException {
         Room room = modelMapper.map(roomDTO, Room.class);
        List<BedDTO> availableBeds = bedService.findBedsByRoom(room);

        return availableBeds;
    }



    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);
        Room updatedRoom = roomRepository.save(room);
        return modelMapper.map(updatedRoom, RoomDTO.class);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

//    @Override
//    public List<BedDTO> getAvailableBedsByRoom(Long roomId) {
//      //  roomRepository.
//        return null;
//    }
}
