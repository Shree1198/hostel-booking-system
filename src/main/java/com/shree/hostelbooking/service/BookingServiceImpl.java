package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Booking;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import com.shree.hostelbooking.repository.BookingRepository;
import com.shree.hostelbooking.util.BookingMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final BedService bedService;
    private final ModelMapper modelMapper;

    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, BedService bedService, ModelMapper modelMapper, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.bedService = bedService;
        this.modelMapper = modelMapper;
        this.bookingMapper = bookingMapper;
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


//        Get those bed from particular room where checkIn time
        List<BedDTO> availableBeds = bedService.findBedsByRoom(roomId);//roomService.getAvailableBedsByRoom(roomDTO);
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

        bedDTO.setAvailability(false);

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
    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public BookingDTO getBookingById(Long id) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
      //  return modelMapper.map(booking, BookingDTO.class);

        log.info("getBookingById : {}",booking);
        return bookingMapper.toDto(booking);
    }

   @Override
    @Transactional
    public String cancelBooking(Long bookingId) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        Bed bed = booking.getBed();
        bed.setAvailability(true);
        bedService.updateBed(modelMapper.map(bed, BedDTO.class));

        bookingRepository.delete(booking);

        return "Booking with ID " + bookingId + " has been canceled successfully.";
    }

}
