package com.bhakti.bktapijava2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "Mst_Config")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MstConfig {

    @Column("ConfigName")
    private String configName;

    @Column("ConfigType")
    private String configType;

    @Column("ConfigDesc")
    private String configDesc;

    @Column("CreatedBy")
    private String createdBy;

    @Column("CreatedDate")
    private LocalDateTime createdDate;

    @Column("ConfigValue")
    private String configValue;

}
