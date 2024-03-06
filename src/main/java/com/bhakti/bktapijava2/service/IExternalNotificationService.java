package com.bhakti.bktapijava2.service;

public interface IExternalNotificationService {
    void sendEmailToSomebody(String bodyEmail, String subject, String branch);
}
