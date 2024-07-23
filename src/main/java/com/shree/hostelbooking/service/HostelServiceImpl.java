package com.shree.hostelbooking.service;

import com.shree.hostelbooking.entity.Hostel;
import com.shree.hostelbooking.repository.HostelRepository;
import com.shree.hostelbooking.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelServiceImpl implements HostelService {

    @Autowired
    private HostelRepository hostelRepository;

    @Override
    public List<Hostel> findAll() {
        return hostelRepository.findAll();
    }

    @Override
    public Optional<Hostel> findById(Long id) {
        return hostelRepository.findById(id);
    }

    @Override
    public Hostel save(Hostel hostel) {
        return hostelRepository.save(hostel);
    }

    @Override
    public void deleteById(Long id) {
        hostelRepository.deleteById(id);
    }
}
