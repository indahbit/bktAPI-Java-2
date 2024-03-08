package com.bhakti.bktapijava2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GlobalHandledErrorException extends RuntimeException {

    private String message;

    private static int classCode = 100;

    public GlobalHandledErrorException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
