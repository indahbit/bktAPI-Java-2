package com.bhakti.bktapijava2.scheduler;

import com.bhakti.bktapijava2.service.ITandaTerimaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MergePDFScheduler {

    @Autowired
    private ITandaTerimaService tandaTerimaService;

    @Scheduled(cron = "0 59 23 * * ?")
    public void mergePdfFKSJFPJob() {
        tandaTerimaService.mergePdfByNoKwitansi();

        log.info("Job merge selesai dijalankan.");
    }
}
