package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingConsumerDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ItemService itemService;
    private final UserService userService;

    private final ModelMapper modelMapper;

    @Override
    public Booking create(BookingConsumerDto bookingConsumerDto, Long userId) throws ItemNotFoundException, UserNotFoundException, ItemAvailableException {
        userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("user not found"));
        Item item = itemRepository.findById(bookingConsumerDto.getItemId()).orElseThrow(()
                -> new ItemNotFoundException("item not found"));
        if (!item.getAvailable()) {
            throw new ItemAvailableException("item valid error");
        }
        if (userId.equals(item.getOwner().getId())) {
            throw new UserNotFoundException("");
        }
        Booking booking = modelMapper.map(bookingConsumerDto, Booking.class);
        booking.setItem(modelMapper.map(itemRepository.findById(bookingConsumerDto.getItemId()), Item.class));
        booking.setBooker(userService.getById(userId));
        booking.setStatus(Status.WAITING);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking approved(Long userId, Long bookingId, Boolean approve) throws BookingNotFoundException, UserNotFoundException, ItemAvailableException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new BookingNotFoundException("booking not found"));
        if (booking.getStatus().equals(Status.APPROVED)) {
            throw new ItemAvailableException("");
        }
        if (!itemRepository.findById(booking.getItem().getId()).get().getOwner().getId().equals(userId)) {
            throw new UserNotFoundException("user not found");
        }
        booking.setStatus(approve ? Status.APPROVED : Status.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getById(Long userId, Long bookingId) throws BookingNotFoundException, UserNotFoundException {
        userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("user not found"));
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new BookingNotFoundException("booking not found"));
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return booking;
        } else {
            throw new BookingNotFoundException("");
        }
    }

    @Override
    public List<Booking> getAllBookingsByUserId(Long userId, String state) throws UserNotFoundException, NotSupportException {
        userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("user not found"));
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case "WAITING":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            case "PAST":
                return bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, now);
            case "FUTURE":
                return bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, now);
            case "CURRENT":
                return bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            case "ALL":
                return bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
            default:
                throw new NotSupportException();
        }
    }

    @Override
    public List<Booking> getAllBookingsForOwner(Long userId, String state) throws UserNotFoundException, NotSupportException {
        userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("user not found"));
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case "WAITING":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            case "PAST":
                return bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, now);
            case "FUTURE":
                return bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(userId, now);
            case "CURRENT":
                return bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            case "ALL":
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
            default:
                throw new NotSupportException();
        }
    }
}
