package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingConsumerDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrey Boyarov
 */
public interface BookingService {

    Booking create(BookingConsumerDto bookingConsumerDto, Long userId) throws UserNotFoundException, ItemAvailableException, ItemNotFoundException, ItemValidException, UserValidException;

    Booking approved(Long userId, Long bookingId, Boolean approve) throws BookingNotFoundException, UserNotFoundException, ItemAvailableException;

    Booking getById(Long userId ,Long bookingId) throws BookingNotFoundException, BookingValidException, UserNotFoundException;

    List<Optional<Booking>> getAllBookingsByUserId(Long userId, String state) throws UserNotFoundException, ItemAvailableException, NotSupportException;

    List<Optional<Booking>> getAllBookingsForOwner(Long userId, String state) throws UserNotFoundException, NotSupportException;

}
