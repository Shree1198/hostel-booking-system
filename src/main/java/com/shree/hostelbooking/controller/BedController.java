package com.shree.hostelbooking.controller;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @PostMapping("/save/{roomId}")
    public ResponseEntity<List<BedDTO>> saveBeds(@RequestBody List<Bed> beds, @PathVariable Long roomId) {
        List<BedDTO> savedBeds = bedService.saveBeds(beds, roomId);
        return ResponseEntity.ok(savedBeds);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BedDTO>> findBedsByRoom(@PathVariable Long roomId) {
        List<BedDTO> beds = bedService.findBedsByRoom(roomId);
        return ResponseEntity.ok(beds);
    }

    @PutMapping("/update")
    public ResponseEntity<BedDTO> updateBed(@RequestBody BedDTO bedDTO) {
        BedDTO updatedBed = bedService.updateBed(bedDTO);
        return ResponseEntity.ok(updatedBed);
    }
}
