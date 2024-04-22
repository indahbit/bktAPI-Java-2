package com.bhakti.bktapijava2.service.impl;

import com.bhakti.bktapijava2.dto.request.SendEmailRequestDto;
import com.bhakti.bktapijava2.entity.CofEmailJob;
import com.bhakti.bktapijava2.exception.GlobalHandledErrorException;
import com.bhakti.bktapijava2.proxy.MyCompanyServiceProxy;
import com.bhakti.bktapijava2.repository.jdbc.ICofEmailJobCrudRepository;
import com.bhakti.bktapijava2.service.IExternalNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class ExternalNotificationService implements IExternalNotificationService {

    @Autowired
    private MyCompanyServiceProxy myCompanyServiceProxy;

    @Autowired
    private ICofEmailJobCrudRepository cofEmailJobCrudRepository;

    @Override
    public void sendEmailToFinanceAR(String bodyEmail, String subject, String branch) {
        CofEmailJob cofEmailJob = cofEmailJobCrudRepository.findByNamaJobAndAktifIsTrue("FINANCE AR")
                .orElseThrow(() -> new GlobalHandledErrorException("Job bernama FINANCE AR pada tabel Cof_Email_Job tidak ditemukan"));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        SendEmailRequestDto sendEmailRequestDto = new SendEmailRequestDto();
        sendEmailRequestDto.setTo(cofEmailJob.getEmailAddress());
        sendEmailRequestDto.setCc("");
        sendEmailRequestDto.setBcc("");
        sendEmailRequestDto.setBranch(branch);
        sendEmailRequestDto.setSubject(subject + " (" + now.format(formatter) + ")");
        sendEmailRequestDto.setMessage(bodyEmail);

        myCompanyServiceProxy.sendEmail(sendEmailRequestDto);
    }

}
