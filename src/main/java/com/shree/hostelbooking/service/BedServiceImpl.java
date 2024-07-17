package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import com.shree.hostelbooking.repository.BedRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;

    private final RoomService roomService;

    private final ModelMapper modelMapper;

    public BedServiceImpl(BedRepository bedRepository, RoomService roomService, ModelMapper modelMapper) {
        this.bedRepository = bedRepository;
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BedDTO> findBedsByRoom(Long roomId) throws ResourceNotFoundException {

        List<Bed> availableBeds = bedRepository.findByRoomAndIsAvailable(roomId);

        log.info("BedServiceImpl.findBedsByRoom --> :{}", availableBeds);
        return availableBeds.stream().map(this::bedToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BedDTO> saveBeds(List<Bed> beds, Long roomId) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);

        beds.stream().peek(bed -> bed.setRoom(modelMapper.map(roomDTO, Room.class))).collect(Collectors.toList());


        List<Bed> bedList = bedRepository.saveAll(beds);

        return bedList.stream().map(this::bedToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BedDTO updateBed(BedDTO bedDTO) {

        //Bed bed = modelMapper.map(bedDTO, Bed.class);
        Bed bed = bedRepository.findById(bedDTO.getId()).get();
        bed.setAvailability(false);
        //  bed.setRoom(modelMapper.map(bedDTO.getRoomDTO());

//        Bed updatedBed = bedRepository.save(bed);
        return modelMapper.map(bed, BedDTO.class);
    }

    @Override
    public void deleteBed(Long bedId) throws ResourceNotFoundException {
        Bed bed = bedRepository.findById(bedId).orElseThrow(() -> new ResourceNotFoundException("Bed not found with ID: " + bedId));
        bedRepository.delete(bed);
        log.info("Bed with ID {} has been deleted", bedId);
    }

    private BedDTO bedToDTO(Bed bed) {

        BedDTO bedDTO = new BedDTO();
        bedDTO.setId(bed.getId());
        bedDTO.setAvailability(bed.isAvailability());
        bedDTO.setRoom(modelMapper.map(bed.getRoom(), RoomDTO.class));
        return bedDTO;
    }
}
