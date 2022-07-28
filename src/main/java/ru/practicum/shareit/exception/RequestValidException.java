package ru.practicum.shareit.exception;

/**
 * @author Andrey Boyarov
 */
public class RequestValidException extends Exception{
    public RequestValidException(String message) {
        super(message);
    }
}
