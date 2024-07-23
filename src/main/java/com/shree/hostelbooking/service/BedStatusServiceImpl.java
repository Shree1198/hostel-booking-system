package com.shree.hostelbooking.service;

import com.shree.hostelbooking.entity.BedStatus;
import com.shree.hostelbooking.repository.BedStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BedStatusServiceImpl implements BedStatusService {

    private final BedStatusRepository bedStatusRepository;

    @Autowired
    public BedStatusServiceImpl(BedStatusRepository bedStatusRepository) {
        this.bedStatusRepository = bedStatusRepository;
    }

    @Override
    public List<BedStatus> getAllBedStatuses() {
        return bedStatusRepository.findAll();
    }

    @Override
    public Optional<BedStatus> getBedStatusById(Long id) {
        return bedStatusRepository.findById(id);
    }

    @Override
    public BedStatus saveBedStatus(BedStatus bedStatus) {
        return bedStatusRepository.save(bedStatus);
    }

    @Override
    public void deleteBedStatus(Long id) {
        bedStatusRepository.deleteById(id);
    }

    @Override
    public void cancelBed(Long id) {

    }

    @Override
    public List<Long> findAvailableBeds(Long roomId, LocalDateTime givenDate) {
        return   bedStatusRepository.findAvailableBeds(roomId,givenDate);
    }

    @Override
    public List<Long> bedsAlreadyBookedForToday(Long roomId, LocalDate checkIn) {
        return bedStatusRepository.bedsAlreadyBookedForToday(roomId,checkIn);
    }
}
