package com.bhakti.bktapijava2.repository.jdbc;

import com.bhakti.bktapijava2.dto.query_result.CofTagihanKwitansiInputOutputLocationDto;

public interface ICofTagihanKwitansiJdbcRepository {
    CofTagihanKwitansiInputOutputLocationDto findInputAndOutputPdfPath();
}
