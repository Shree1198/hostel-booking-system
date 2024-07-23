package com.shree.hostelbooking.service;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.BedStatus;
import com.shree.hostelbooking.entity.Booking;
import com.shree.hostelbooking.exception.ResourceNotFoundException;
import com.shree.hostelbooking.repository.BookingRepository;
import com.shree.hostelbooking.util.BookingMapper;
import com.shree.hostelbooking.util.BookingUtils;
import com.shree.hostelbooking.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final BedStatusService bedStatusService;
    private final ModelMapper modelMapper;

    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomService roomService,
                              BedService bedService,
                              BedStatusService bedStatusService,
                              BedStatusService bedStatusService1,
                              ModelMapper modelMapper,
                              BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.bedService = bedService;
        this.bedStatusService = bedStatusService1;

        this.modelMapper = modelMapper;
        this.bookingMapper = bookingMapper;
    }


    @Override
    public BookingDTO createBooking(Long roomId, String user, LocalDateTime checkIn, LocalDateTime checkOut) throws ResourceNotFoundException {

     // checkout date is not given
        if(checkOut == null){
            checkOut = BookingUtils.getDefaultCheckOut(checkIn);
        }

//        Get All bed from particular room
        List<BedDTO> availableBeds = bedService.findBedsByRoom(roomId);//roomService.getAvailableBedsByRoom(roomDTO);
        log.info("availableBeds - {}", availableBeds);

        log.info("roomId - {}  checkIn {}", roomId,checkIn);
        List<Long> canBeBookBeds = bedStatusService.findAvailableBeds(roomId,checkIn);

        log.info("canBeBookBeds - {}", canBeBookBeds);

        BedDTO bedToBook = null;

        if(canBeBookBeds.isEmpty()){
            if(availableBeds.isEmpty()){
                throw new ResourceNotFoundException("No available beds in the room");
            }else{
                // is any booking has happened before this with same room and same date then
                // exclude that bed of room

               List<Long> bedsAleredyBookedToday  = bedStatusService.bedsAlreadyBookedForToday(roomId,checkIn.toLocalDate());
               log.info("bedsAleredyBookedToday {}",bedsAleredyBookedToday);
               if(bedsAleredyBookedToday != null && !bedsAleredyBookedToday.isEmpty()){
                   bedToBook = availableBeds.stream()
                           .filter(bed -> !bedsAleredyBookedToday.contains(bed.getId()))
                           .collect(Collectors.toList()).get(0);
               }else{
                   log.info("in here");
                   bedToBook = availableBeds.get(0);
               }

            }
        } else{
            if(canBeBookBeds.size() == 1){
                bedToBook =  availableBeds.stream().filter( bed -> bed.getId() == canBeBookBeds.get(0)).collect(Collectors.toList()).get(0);
            }else{
                //
                List<BedDTO> currentAvailableBed  =  availableBeds.stream().filter( bed -> bed.getId() == canBeBookBeds.get(0)).collect(Collectors.toList());
                bedToBook = currentAvailableBed.get(0);
            }
        }

        // Ensure availableBeds is not null
        if (bedToBook == null ) {
            throw new ResourceNotFoundException("Bed Booking Not Successful");
        }

        Booking booking = new Booking();
        //  booking.setRoom(modelMapper.map(roomDTO, Room.class));
        booking.setBed(modelMapper.map(bedToBook, Bed.class));
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);


        //
        BedStatus bedStatus = new BedStatus();
        bedStatus.setBedId(bedToBook.getId());
        bedStatus.setRoomId(roomId);
        bedStatus.setCheckIn(checkIn);
        bedStatus.setCheckOut(checkOut);
        bedStatus.setStatus(Status.BOOKED);

        bedStatusService.saveBedStatus(bedStatus);
        BookingDTO bookingDTO = modelMapper.map(savedBooking, BookingDTO.class);
        bookingDTO.setCheckIn(checkIn);
        bookingDTO.setCheckOut(checkOut);
        log.info("saved booking - {}", savedBooking);
        return bookingDTO;
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
       // bed.setAvailability(true);
        bedService.updateBed(modelMapper.map(bed, BedDTO.class));

        bookingRepository.delete(booking);

        return "Booking with ID " + bookingId + " has been canceled successfully.";
    }

}
