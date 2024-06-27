package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Booking;
import com.shree.hostelbooking.entity.Room;
import com.shree.hostelbooking.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final RoomService roomService;

    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingDTO createBooking(Long roomId, String user, LocalDate checkIn, LocalDate checkOut) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);
        log.info("room found : {}", roomDTO);
        if (roomDTO == null || !roomDTO.isAvailable()) {
            throw new RuntimeException("Room is not available");
        }

        Booking booking = new Booking();
        booking.setRoom(modelMapper.map(roomDTO, Room.class)); // Assuming Room constructor with id
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);

        roomDTO.setAvailable(false);
        roomService.updateRoom(roomDTO);

        log.info("booking created : {}", booking);
        Booking savedBooking = bookingRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        return (booking != null) ? convertToDTO(booking) : null;
    }

    @Override
    public String cancelBooking(Long bookingId) {
        //get the booking

        // get room get bed set as available

        //set status of booking as canceled

        return null;
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setRoomId(booking.getRoom().getId());
        bookingDTO.setUser(booking.getUser());
        bookingDTO.setBookingDateTime(booking.getBookingTime());
        bookingDTO.setCheckIn(booking.getCheckIn());
        bookingDTO.setCheckOut(booking.getCheckOut());
        return bookingDTO;
    }
}
