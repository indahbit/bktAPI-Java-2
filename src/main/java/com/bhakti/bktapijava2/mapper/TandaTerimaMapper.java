package com.bhakti.bktapijava2.mapper;

import com.bhakti.bktapijava2.dto.query_result.TandaTerimaWithKdPlgAndNmPlgDTO;
import com.bhakti.bktapijava2.entity.TandaTerima;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TandaTerimaMapper {

    public TandaTerimaWithKdPlgAndNmPlgDTO tandaTerimaWithKdPlgAndNmPlg(ResultSet rs) throws SQLException {
        TandaTerimaWithKdPlgAndNmPlgDTO tandaTerima = new TandaTerimaWithKdPlgAndNmPlgDTO();
        tandaTerima.setKdPlg(rs.getString("Kd_Plg"));
        tandaTerima.setNmPlg(rs.getString("nm_plg"));
        tandaTerima.setNoFaktur(rs.getString("no_faktur").trim());
        tandaTerima.setNoKwitansi(rs.getString("no_kwitansi"));
        tandaTerima.setTglKwitansi(rs.getTimestamp("tgl_kwitansi").toLocalDateTime());
        tandaTerima.setNoSuratJalan(rs.getString("no_sj").trim());
        tandaTerima.setNoFakturPajak(rs.getString("No_FakturP").trim());
        tandaTerima.setKategoriBarang(rs.getString("Kategori_Brg").trim());

        return tandaTerima;
    }

    public TandaTerima toTandaTerima(ResultSet rs) throws SQLException {
        TandaTerima tandaTerima = new TandaTerima();

        tandaTerima.setNourut(rs.getInt("nourut"));
        tandaTerima.setNoTandaTerima(rs.getString("No_TandaTerima"));
        tandaTerima.setNoFaktur(rs.getString("No_Faktur").trim());
        tandaTerima.setTglTandaTerima(rs.getDate("Tgl_TandaTerima"));
        tandaTerima.setTglBayar(rs.getDate("Tgl_Bayar"));
        tandaTerima.setCheck(rs.getString("Check"));
        tandaTerima.setTerlambat(rs.getBoolean("Terlambat"));
        tandaTerima.setTotalPelunasan(rs.getDouble("Total_Pelunasan"));
        tandaTerima.setKet(rs.getString("Ket"));
        tandaTerima.setMerk(rs.getString("Merk"));
        tandaTerima.setCetak(rs.getBoolean("Cetak"));
        tandaTerima.setEntryTime(rs.getDate("Entry_Time"));
        tandaTerima.setUserName(rs.getString("User_Name"));
        tandaTerima.setNoKwitansi(rs.getString("No_Kwitansi"));
        tandaTerima.setTglKwitansi(rs.getDate("Tgl_Kwitansi"));
        tandaTerima.setPrinted(rs.getBoolean("IsPrinted"));
        tandaTerima.setPrintedBy(rs.getString("PrintedBy"));
        tandaTerima.setPrintedDate(rs.getDate("PrintedDate"));
        tandaTerima.setEMeteraiValue(rs.getDouble("EMeteraiValue"));
        tandaTerima.setEMeteraiSN(rs.getString("EMeteraiSN"));
        tandaTerima.setKwitansiFileName(rs.getString("KwitansiFileName"));
        tandaTerima.setNilaiKwitansi(rs.getDouble("Nilai_Kwitansi"));
        tandaTerima.setNoTT(rs.getString("No_TT"));
        tandaTerima.setKwitansiCreatedBy(rs.getString("KwitansiCreatedBy"));
        tandaTerima.setKwitansiCreatedDate(rs.getDate("KwitansiCreatedDate"));
        tandaTerima.setTotalFaktur(rs.getDouble("TotalFaktur"));
        tandaTerima.setTotalTT(rs.getDouble("TotalTT"));
        tandaTerima.setDppFaktur(rs.getDouble("DPPFaktur"));
        tandaTerima.setPpnFaktur(rs.getDouble("PPNFaktur"));
        tandaTerima.setMergePDF(rs.getBoolean("MergePDF"));
        tandaTerima.setMergeBy(rs.getString("MergeBy"));
        tandaTerima.setMergeDate(rs.getDate("MergeDate"));

        return tandaTerima;
    }
}
