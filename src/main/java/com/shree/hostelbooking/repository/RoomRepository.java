package com.shree.hostelbooking.repository;


import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomNumber(String roomNumber);

//    @Query("SELECT b FROM Room r JOIN r.beds b WHERE r.id = :roomId AND b.isBooked = false")
//    List<Bed> findNonBookedBedsByRoomId(@Param("roomId") Long roomId);
}
