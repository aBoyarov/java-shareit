package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingConsumerDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.*;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface BookingService {

    Booking create(BookingConsumerDto bookingConsumerDto, Long userId) throws UserNotFoundException, ItemAvailableException, ItemNotFoundException;

    Booking approved(Long userId, Long bookingId, Boolean approve) throws BookingNotFoundException, UserNotFoundException, ItemAvailableException;

    Booking getById(Long userId ,Long bookingId) throws BookingNotFoundException, BookingValidException, UserNotFoundException;

    List<Booking> getAllBookingsByUserId(Long userId, String state) throws UserNotFoundException, ItemAvailableException, NotSupportException;

    List<Booking> getAllBookingsForOwner(Long userId, String state) throws UserNotFoundException, NotSupportException;

}
