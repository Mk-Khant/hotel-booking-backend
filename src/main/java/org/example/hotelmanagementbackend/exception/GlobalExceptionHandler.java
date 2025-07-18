package org.example.hotelmanagementbackend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserNameAlreadyExistException.class,
            RoomNotFoundException.class})
    public ResponseEntity exceptionHandler(Exception ex, WebRequest request) throws Exception {
        return handleExceptionInternal(ex, message(ex), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    public ApiError message(Exception ex){
        String message = Objects.nonNull(ex) ? ex.getMessage() : "Unknown Error";
        return new ApiError(message, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }
}
