package com.bhakti.bktapijava2.service;

public interface IExternalNotificationService {
    void sendEmailToFinanceAR(String bodyEmail, String subject, String branch);
}
