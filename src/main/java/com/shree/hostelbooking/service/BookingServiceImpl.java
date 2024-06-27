package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
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

    @Override
    public BookingDTO createBooking(Long roomId, String user, LocalDate checkIn, LocalDate checkOut) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);
        List<BedDTO> availableBeds = bedService.getAvailableBedsByRoom(roomId);
        if (availableBeds.isEmpty()) {
            throw new RuntimeException("No available beds in the room");
        }

        BedDTO bedDTO = availableBeds.get(0); // Booking the first available bed

        Booking booking = new Booking();
     //   booking.setRoom(modelMapper.map(roomDTO, Room.class));
        booking.setBed(modelMapper.map(bedDTO, Bed.class));
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);

        bedDTO.setAvailable(false);
        roomService.updateRoom(roomDTO);

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
