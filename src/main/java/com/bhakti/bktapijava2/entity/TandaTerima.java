package com.bhakti.bktapijava2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TblTandaTerima")
public class TandaTerima {

    @Id
    @Column(name = "nourut")
    private int nourut;

    @Column(name = "No_TandaTerima")
    private String noTandaTerima;

    @Column(name = "No_Faktur")
    private String noFaktur;

    @Column(name = "Tgl_TandaTerima")
    private Date tglTandaTerima;

    @Column(name = "Tgl_Bayar")
    private Date tglBayar;

    @Column(name = "Check")
    private String check;

    @Column(name = "Terlambat")
    private boolean terlambat;

    @Column(name = "Total_Pelunasan")
    private Double totalPelunasan;

    @Column(name = "Ket")
    private String ket;

    @Column(name = "Merk")
    private String merk;

    @Column(name = "Cetak")
    private boolean cetak;

    @Column(name = "Entry_Time")
    private Date entryTime;

    @Column(name = "User_Name")
    private String userName;

    @Column(name = "No_Kwitansi")
    private String noKwitansi;

    @Column(name = "Tgl_Kwitansi")
    private Date tglKwitansi;

    @Column(name = "IsPrinted")
    private boolean isPrinted;

    @Column(name = "PrintedBy")
    private String printedBy;

    @Column(name = "PrintedDate")
    private Date printedDate;

    @Column(name = "EMeteraiValue")
    private Double eMeteraiValue;

    @Column(name = "EMeteraiSN")
    private String eMeteraiSN;

    @Column(name = "KwitansiFileName")
    private String kwitansiFileName;

    @Column(name = "Nilai_Kwitansi")
    private Double nilaiKwitansi;

    @Column(name = "No_TT")
    private String noTT;

    @Column(name = "KwitansiCreatedBy")
    private String kwitansiCreatedBy;

    @Column(name = "KwitansiCreatedDate")
    private Date kwitansiCreatedDate;

    @Column(name = "TotalFaktur")
    private Double totalFaktur;

    @Column(name = "TotalTT")
    private Double totalTT;

    @Column(name = "DPPFaktur")
    private Double dppFaktur;

    @Column(name = "PPNFaktur")
    private Double ppnFaktur;

    @Column(name = "MergePDF")
    private boolean mergePDF;

    @Column(name = "MergeBy")
    private String mergeBy;

    @Column(name = "MergeDate")
    private Date mergeDate;

}
