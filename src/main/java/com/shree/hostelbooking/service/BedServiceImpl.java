package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
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
    private ModelMapper modelMapper;

    @Override
    public List<BedDTO> findBedsByRoom(Room room) throws ResourceNotFoundException {

        List<Bed> availableBeds = bedRepository.findByRoomAndIsAvailable(room, true);

        return availableBeds.stream()
                .map(bed -> modelMapper.map(bed, BedDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BedDTO> saveBeds(List<Bed> beds) {

        List<Bed> bedList  = bedRepository.saveAll(beds);

       List<BedDTO> bedDTOS = bedList.stream().map(bed -> bedToDTO(bed)).collect(Collectors.toList());

        return bedDTOS;
    }

    private BedDTO bedToDTO(Bed bed){

        BedDTO bedDTO = new BedDTO();
        bedDTO.setId(bed.getId());
        bedDTO.setAvailable(bed.isAvailable());
        return  bedDTO;
    }
}
