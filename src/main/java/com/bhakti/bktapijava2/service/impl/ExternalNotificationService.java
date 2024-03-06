package com.bhakti.bktapijava2.service.impl;

import com.bhakti.bktapijava2.dto.request.SendEmailRequestDto;
import com.bhakti.bktapijava2.proxy.MyCompanyServiceProxy;
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

    @Override
    public void sendEmailToSomebody(String bodyEmail, String subject, String branch) {
        List<String> emailRecipient = new ArrayList<>();
        emailRecipient.add("soundgraphia@gmail.com");

        String joinedRecipientEmails = String.join(", ", emailRecipient);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        SendEmailRequestDto sendEmailRequestDto = new SendEmailRequestDto();
        sendEmailRequestDto.setTo(joinedRecipientEmails);
        sendEmailRequestDto.setCc("");
        sendEmailRequestDto.setBcc("");
        sendEmailRequestDto.setBranch(branch);
        sendEmailRequestDto.setSubject(subject + " (" + now.format(formatter) + ")");
        sendEmailRequestDto.setMessage(bodyEmail);

        myCompanyServiceProxy.sendEmail(sendEmailRequestDto);
    }

}
