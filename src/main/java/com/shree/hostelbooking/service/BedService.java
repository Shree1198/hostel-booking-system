package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.exception.ResourceNotFoundException;

import java.util.List;

public interface BedService {
   // List<BedDTO> getAvailableBedsByRoom(Long roomId) throws ResourceNotFoundException;

    List<BedDTO> saveBeds(List<Bed> beds, Long roomId);

    List<BedDTO>  findBedsByRoom(Long roomId);

    // Method to update a bed
    BedDTO updateBed(BedDTO bedDTO);

    void deleteBed(Long bedId) throws ResourceNotFoundException;
}
