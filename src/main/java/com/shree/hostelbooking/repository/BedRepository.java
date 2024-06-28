package com.shree.hostelbooking.repository;

import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomAndIsAvailable(Room room, boolean isBedAvailable);
}
