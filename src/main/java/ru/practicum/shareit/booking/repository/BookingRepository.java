package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrey Boyarov
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Optional<Booking>> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                             LocalDateTime beforeTime,
                                                                             LocalDateTime afterTime);
    Integer countByItemIdAndBookerIdAndStatusAndStartBefore(long itemId,
                                                            long userId,
                                                            Status waiting,
                                                            LocalDateTime time);
    List<Optional<Booking>> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long userId,
                                                                                LocalDateTime beforeTime,
                                                                                LocalDateTime afterTime);
    List<Optional<Booking>> findAllByBookerIdOrderByStartDesc(Long userId);

    List<Optional<Booking>> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long userId, Status waiting);

    List<Optional<Booking>> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime time);

    List<Optional<Booking>> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime time);

    List<Optional<Booking>> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long userId, LocalDateTime time);

    List<Optional<Booking>> findAllByBookerIdAndStartAfterOrderByStartDesc(Long userId, LocalDateTime time);

    List<Optional<Booking>> findAllByBookerIdAndStatusOrderByStartDesc(Long userId, Status waiting);

    List<Optional<Booking>> findAllByItemOwnerIdOrderByStartDesc(Long userId);

    Booking getFirstByItemIdOrderByEndDesc(long itemId);

    Booking getFirstByItemIdOrderByStartAsc(long itemId);

}
