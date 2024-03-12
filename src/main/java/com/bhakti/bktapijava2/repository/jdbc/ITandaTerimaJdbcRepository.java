package com.bhakti.bktapijava2.repository.jdbc;



import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import com.bhakti.bktapijava2.entity.TandaTerima;

import java.util.List;

public interface ITandaTerimaJdbcRepository {
    List<TandaTerimaWithKdPlgAndNmPlgDTO> findByMergePDFisFalse();

    void updateMergePdfByNoFakturList(List<String> fakturList, boolean mergedPdf);
}
