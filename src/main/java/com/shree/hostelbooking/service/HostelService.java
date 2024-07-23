package com.shree.hostelbooking.service;

import com.shree.hostelbooking.entity.Hostel;

import java.util.List;
import java.util.Optional;

public interface HostelService {
    List<Hostel> findAll();
    Optional<Hostel> findById(Long id);
    Hostel save(Hostel hostel);
    void deleteById(Long id);
}
