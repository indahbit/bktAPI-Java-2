package com.bhakti.bktapijava2.repository.jdbc.impl;

import com.bhakti.bktapijava2.dto.query_result.CofTagihanKwitansiInputOutputLocationDto;
import com.bhakti.bktapijava2.mapper.CofTagihanKwitansiMapper;
import com.bhakti.bktapijava2.repository.jdbc.ICofTagihanKwitansiJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CofTagihanKwitansiJdbcRepository implements ICofTagihanKwitansiJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private CofTagihanKwitansiMapper tagihanKwitansiMapper;

    @Override
    public CofTagihanKwitansiInputOutputLocationDto findInputAndOutputPdfPath() {
        String query = """
                    SELECT INPUT_KWITANSI_MO_PDF_PATH, OUTPUT_KWITANSI_MO_PDF_PATH from Cof_TagihanKwitansi
                """;

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> tagihanKwitansiMapper.toCofTagihanKwitansiInputOutputLocation(rs));
    }

}
