package com.bhakti.bktapijava2.service.impl;

import com.bhakti.bktapijava2.entity.MstConfig;
import com.bhakti.bktapijava2.exception.GlobalHandledErrorException;
import com.bhakti.bktapijava2.repository.jdbc.IMstConfigCrudRepository;
import com.bhakti.bktapijava2.service.IMstConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MstConfigService implements IMstConfigService {
    @Autowired
    private IMstConfigCrudRepository mstConfigCrudRepository;

    @Override
    public MstConfig getConfigByConfigNameIsVaPaymentConsumer() {
        return mstConfigCrudRepository.findByConfigNameAndConfigType("BktAPIJava2", "ChangeLog")
                .orElseThrow(() -> new GlobalHandledErrorException("Config ChangeLog tidak ditemukan"));
    }
}
