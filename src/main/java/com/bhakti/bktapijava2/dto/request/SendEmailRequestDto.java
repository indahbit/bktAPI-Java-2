package com.bhakti.bktapijava2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequestDto {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String message;
    private String branch;
}
