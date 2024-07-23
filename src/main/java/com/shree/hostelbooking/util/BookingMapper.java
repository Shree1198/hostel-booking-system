package com.shree.hostelbooking.util;

import com.shree.hostelbooking.dto.BedDTO;
import com.shree.hostelbooking.dto.BookingDTO;
import com.shree.hostelbooking.dto.RoomDTO;
import com.shree.hostelbooking.entity.Bed;
import com.shree.hostelbooking.entity.Booking;
import com.shree.hostelbooking.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;



@Mapper(componentModel = "spring",nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "bed", target = "bed")
    @Mapping(source = "bed.room.roomId", target = "bed.room.id")
    @Mapping(target = "bed.availability", ignore = true)
    BookingDTO toDto(Booking booking);

    List<BookingDTO> toDtoList(List<Booking> bookings);

    BedDTO toDto(Bed bed);

    RoomDTO toDto(Room room);
}