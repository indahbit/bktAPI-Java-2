package com.bhakti.bktapijava2.proxy;

import com.bhakti.bktapijava2.dto.ErrorResponseDTO;
import com.bhakti.bktapijava2.dto.request.SendEmailRequestDto;
import com.bhakti.bktapijava2.exception.GlobalHandledErrorException;
import com.bhakti.bktapijava2.repository.jdbc.IConfigRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MyCompanyServiceProxy {

    @Autowired
    private IConfigRepository configRepository;

    public void sendEmail(SendEmailRequestDto sendEmailRequestDto) {
        String baseUrl = configRepository.findMyCompanyServerAddress();
        String requestUrl = baseUrl + "messageGateway/SendEmail";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(sendEmailRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    requestUrl,
                    requestBody,
                    String.class
            );

            String responseBody = responseEntity.getBody();
            if (responseBody != null) {
                log.info(responseBody.trim());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 403 && e.getResponseBodyAsString().trim().replace("\"", "").equals("FAILED")) {
                throw new GlobalHandledErrorException(e.getResponseBodyAsString());
            }
        }

    }
}
