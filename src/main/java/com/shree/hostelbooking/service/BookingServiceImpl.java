package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Booking;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import com.shree.hostelbooking.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final BedService bedService;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, BedService bedService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.bedService = bedService;
        this.modelMapper = modelMapper;
    }

//    @Override
//    public BookingDTO createBooking(Long roomId, String user, LocalDate checkIn, LocalDate checkOut) throws ResourceNotFoundException {
//        RoomDTO roomDTO = roomService.getRoomById(roomId);
//        List<BedDTO> availableBeds = roomService.getAvailableBedsByRoom(roomDTO);
//
//        if (availableBeds.isEmpty()) {
//            throw new ResourceNotFoundException("No available beds in the room");
//        }
//
//        BedDTO bedDTO = availableBeds.get(0); // Booking the first available bed
//
//        bedDTO.setAvailable(false);
//
//        Booking booking = new Booking();
//        booking.setRoom(modelMapper.map(roomDTO, Room.class));
//        booking.setBed(modelMapper.map(bedDTO, Bed.class));
//        booking.setUser(user);
//        booking.setBookingTime(LocalDateTime.now());
//        booking.setCheckIn(checkIn);
//        booking.setCheckOut(checkOut);
//
//
//      //  roomService.updateRoom(roomDTO);
//
//        Booking savedBooking = bookingRepository.save(booking);
//        return modelMapper.map(savedBooking, BookingDTO.class);
//    }

    @Override
    public BookingDTO createBooking(Long roomId, String user, LocalDate checkIn, LocalDate checkOut) throws ResourceNotFoundException {
//        RoomDTO roomDTO = roomService.getRoomById(roomId);

      // log.info("RoomDTO - {}", roomDTO);

        // Ensure roomDTO is not null
//        if (roomDTO == null) {
//            throw new ResourceNotFoundException("Room not found with ID: " + roomId);
//        }

        List<BedDTO> availableBeds = bedService.findBedsByRoom(roomId) ;//roomService.getAvailableBedsByRoom(roomDTO);
        log.info("availableBeds - {}", availableBeds);
        // Ensure availableBeds is not null
        if (availableBeds == null || availableBeds.isEmpty()) {
            throw new ResourceNotFoundException("No available beds in the room");
        }

        BedDTO bedDTO = availableBeds.get(0); // Booking the first available bed

        // Ensure bedDTO is not null
        if (bedDTO == null) {
            throw new ResourceNotFoundException("No available beds in the room");
        }

        bedDTO.setAvailable(false);

        Booking booking = new Booking();
      //  booking.setRoom(modelMapper.map(roomDTO, Room.class));
        booking.setBed(modelMapper.map(bedDTO, Bed.class));
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);

        // Ensure bed is updated as unavailable in the database
        bedService.updateBed(bedDTO); // Assuming bedService has an update method

        Booking savedBooking = bookingRepository.save(booking);

        log.info("saved booking - {}", savedBooking);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }


    @Override
    public BookingDTO getBookingById(Long id) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return modelMapper.map(booking, BookingDTO.class);
    }

    @Override
    public String cancelBooking(Long bookingId) {
        //get the booking

        // get room get bed set as available

        //set status of booking as canceled
        return null;
    }
}
