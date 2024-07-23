package com.shree.hostelbooking.service;

import com.shree.hostelbooking.entity.BedStatus;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BedStatusService {

    List<BedStatus> getAllBedStatuses();

    Optional<BedStatus> getBedStatusById(Long id);

    BedStatus saveBedStatus(BedStatus bedStatus);

    void deleteBedStatus(Long id);

    void cancelBed(Long id);

    List<Long> findAvailableBeds( Long roomId,  LocalDateTime givenDate);

    List<Long>  bedsAlreadyBookedForToday(Long roomId, LocalDate checkIn);
}
