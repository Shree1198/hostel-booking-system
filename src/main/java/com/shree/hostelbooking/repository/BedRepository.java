package com.shree.hostelbooking.repository;

import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {
    //  List<Bed> findByRoomAndIsAvailable(Room room, boolean isBedAvailable);

    //    @Query("SELECT b FROM Bed b WHERE b.room.id = :roomId AND b.availability = true")

    @Query("SELECT b FROM Bed b WHERE b.room.id = :roomId")
    List<Bed> findByRoomAndIsAvailable(@Param("roomId") Long roomId);
}
