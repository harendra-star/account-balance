package com.me.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Priority;

@ControllerAdvice
@Priority(100)
public class FileReaderApiExceptionHandler {
    @ExceptionHandler(FileReaderApiException.class)
    public ResponseEntity<Object> handlePersonApiException(
            FileReaderApiException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
    }
}
