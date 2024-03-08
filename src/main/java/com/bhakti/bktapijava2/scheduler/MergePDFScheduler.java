package com.bhakti.bktapijava2.scheduler;

import com.bhakti.bktapijava2.service.ITandaTerimaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MergePDFScheduler {

    @Value("${mergepdf.cron}")
    private String mergepdfCronParameter;

    @Autowired
    private ITandaTerimaService tandaTerimaService;

    @Scheduled(cron = "${mergepdf.cron}")
    public void mergePdfFKSJFPJob() {
        log.info("Cron config value : " + mergepdfCronParameter);
        tandaTerimaService.mergePdfByNoKwitansi();

        log.info("Job merge selesai dijalankan.");
    }
}
