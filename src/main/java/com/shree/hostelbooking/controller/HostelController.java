package com.shree.hostelbooking.controller;

import com.shree.hostelbooking.entity.Hostel;
import com.shree.hostelbooking.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hostels")
public class HostelController {

    @Autowired
    private HostelService hostelService;

    @GetMapping
    public List<Hostel> getAllHostels() {
        return hostelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hostel> getHostelById(@PathVariable Long id) {
        Optional<Hostel> hostel = hostelService.findById(id);
        if (hostel.isPresent()) {
            return ResponseEntity.ok(hostel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Hostel createHostel(@RequestBody Hostel hostel) {
        return hostelService.save(hostel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hostel> updateHostel(@PathVariable Long id, @RequestBody Hostel hostelDetails) {
        Optional<Hostel> hostelOptional = hostelService.findById(id);
        if (hostelOptional.isPresent()) {
            Hostel hostel = hostelOptional.get();
            hostel.setName(hostelDetails.getName());
            hostel.setEmailId(hostelDetails.getEmailId());
            hostel.setAddress(hostelDetails.getAddress());
            hostel.setContact(hostelDetails.getContact());
            hostel.setTotalFloor(hostelDetails.getTotalFloor());
            hostel.setRooms(hostelDetails.getRooms());
            return ResponseEntity.ok(hostelService.save(hostel));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHostel(@PathVariable Long id) {
        Optional<Hostel> hostelOptional = hostelService.findById(id);
        if (hostelOptional.isPresent()) {
            hostelService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
