package com.bhakti.bktapijava2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {

    private int httpStatus;

    private String error;

    private String message;

    private long timestamp;
}
