package ru.practicum.shareit.exception;

/**
 * @author Andrey Boyarov
 */
public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(String message) {
        super(message);
    }
}