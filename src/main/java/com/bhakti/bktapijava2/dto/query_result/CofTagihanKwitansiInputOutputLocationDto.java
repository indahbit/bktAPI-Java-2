package com.bhakti.bktapijava2.dto.query_result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CofTagihanKwitansiInputOutputLocationDto {

    private String inputKwitansiMOPdfPath;

    private String outputKwitansiMOPdfPath;

}
