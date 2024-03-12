package com.bhakti.bktapijava2.mapper;

import com.bhakti.bktapijava2.dto.query_result.CofTagihanKwitansiInputOutputLocationDto;
import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CofTagihanKwitansiMapper {

    public CofTagihanKwitansiInputOutputLocationDto toCofTagihanKwitansiInputOutputLocation(ResultSet rs) throws SQLException {
        CofTagihanKwitansiInputOutputLocationDto cofTagihanKwitansiInputOutputLocationDto = new CofTagihanKwitansiInputOutputLocationDto();
        cofTagihanKwitansiInputOutputLocationDto.setInputKwitansiMOPdfPath(rs.getString("INPUT_KWITANSI_MO_PDF_PATH"));
        cofTagihanKwitansiInputOutputLocationDto.setOutputKwitansiMOPdfPath(rs.getString("OUTPUT_KWITANSI_MO_PDF_PATH"));

        return cofTagihanKwitansiInputOutputLocationDto;
    }

}
