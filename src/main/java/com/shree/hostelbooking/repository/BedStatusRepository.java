package com.shree.hostelbooking.repository;

import com.shree.hostelbooking.entity.BedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BedStatusRepository extends JpaRepository<BedStatus, Long> {

    @Query("SELECT b.bedId FROM BedStatus b WHERE b.roomId = :roomId AND (b.checkOut <= :givenDate OR b.status = 'CANCELED')")
    List<Long> findAvailableBeds(@Param("roomId") Long roomId, @Param("givenDate") LocalDateTime givenDate);

    @Query(value = "SELECT b.bed_id FROM bed_status b WHERE b.room_id = :roomId AND DATE(b.check_in) = DATE(:date)", nativeQuery = true)
    List<Long> bedsAlreadyBookedForToday(@Param("roomId") Long roomId, @Param("date") LocalDate date);
}
