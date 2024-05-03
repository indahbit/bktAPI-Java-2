package com.bhakti.bktapijava2.repository.jdbc.impl;

import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import com.bhakti.bktapijava2.mapper.TandaTerimaMapper;
import com.bhakti.bktapijava2.repository.jdbc.ITandaTerimaJdbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TandaTerimaJdbcRepository implements ITandaTerimaJdbcRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TandaTerimaMapper tandaTerimaMapper;

    @Override
    public List<TandaTerimaWithKdPlgAndNmPlgDTO> findByMergePDFisFalse() {
        String query = """
                    SELECT a.No_Faktur, a.No_Kwitansi, a.Tgl_Kwitansi, b.Kd_Plg, c.nm_plg, b.No_Ref AS no_sj, No_FakturP, b.Kategori_Brg
                    FROM TblTandaTerima a
                    INNER JOIN TblSIHeader b ON a.No_Faktur = b.No_Faktur
                    INNER JOIN TblMsDealer c ON b.Kd_Plg = c.Kd_Plg
                    WHERE MergePDF = 0
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> tandaTerimaMapper.tandaTerimaWithKdPlgAndNmPlg(rs));
    }

    @Override
    public void updateMergePdfByNoFakturList(List<String> fakturList, boolean mergedPdf) {
        String query = "UPDATE TblTandaTerima SET MergePDF = :mergedPdf WHERE No_Faktur IN (:fakturList)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("mergedPdf", mergedPdf);
        parameters.addValue("fakturList", fakturList);

        int a = namedParameterJdbcTemplate.update(query, parameters);
//        int a = jdbcTemplate.update(query, parameters);
        log.info("updated : " + a);
    }


}
