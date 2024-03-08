package com.bhakti.bktapijava2.exception;

import com.bhakti.bktapijava2.dto.ErrorResponseDTO;
import com.bhakti.bktapijava2.dto.RequestResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.time.Instant;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GLobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RequestResponseDTO<ErrorResponseDTO>> handleException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal Server Error", ex.getMessage(), System.currentTimeMillis());
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RequestResponseDTO<>(errorResponse, "FAILED"));
    }

    @ExceptionHandler(GlobalHandledErrorException.class)
    public ResponseEntity<RequestResponseDTO<ErrorResponseDTO>> handledGlobalException(GlobalHandledErrorException ex) {

        ErrorResponseDTO exceptionResponse = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), "FAILED", ex.getMessage(), Instant.now().toEpochMilli());

        log.error("Error occurred while processing the request: {}", getRootCause(ex).getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RequestResponseDTO<>(exceptionResponse, "FAILED"));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<RequestResponseDTO<ErrorResponseDTO>> handledFileNotFoundException(FileNotFoundException ex) {

        ErrorResponseDTO exceptionResponse = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), "FILE NOT FOUND", ex.getMessage(), Instant.now().toEpochMilli());

        log.error("Error occurred while processing the request: {}", getRootCause(ex).getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RequestResponseDTO<>(exceptionResponse, "FAILED"));
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

}
