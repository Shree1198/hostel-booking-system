package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;

import java.util.List;

public interface BedService {
    List<BedDTO> getAvailableBedsByRoom(Long roomId);
}
