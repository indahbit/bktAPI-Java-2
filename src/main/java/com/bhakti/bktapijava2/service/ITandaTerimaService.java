package com.bhakti.bktapijava2.service;

import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import com.bhakti.bktapijava2.entity.TandaTerima;

import java.util.List;

public interface ITandaTerimaService {
    void mergePdfByNoKwitansi();

    List<TandaTerimaWithKdPlgAndNmPlgDTO> getPdfNotMergedTandaTerima();
}
