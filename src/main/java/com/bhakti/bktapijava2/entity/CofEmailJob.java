package com.bhakti.bktapijava2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "Cof_Email_Job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CofEmailJob {

    @Column("Nama_Job")
    private String namaJob;

    @Column("Penerima_Email")
    private String penerimaEmail;

    @Column("Aktif")
    private Boolean aktif;

    @Column("IsUser")
    private Integer isUser;

    @Column("Email_Address")
    private String emailAddress;

    @Column("Tipe_Penerima")
    private String tipePenerima;

}
