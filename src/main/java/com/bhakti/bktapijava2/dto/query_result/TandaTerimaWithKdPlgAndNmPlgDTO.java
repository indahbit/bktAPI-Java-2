package com.bhakti.bktapijava2.dto.query_result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TandaTerimaWithKdPlgAndNmPlgDTO {

    private String noFaktur;

    private String noKwitansi;

    private LocalDateTime tglKwitansi;

    private String kdPlg;

    private String nmPlg;

    private String noSuratJalan;

    private String noFakturPajak;

    private String kategoriBarang;

}
