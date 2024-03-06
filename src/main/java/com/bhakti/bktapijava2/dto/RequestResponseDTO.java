package com.bhakti.bktapijava2.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 7737157297983373542L;

    @Valid
    private T data;

    private String message;

}
