package com.bhakti.bktapijava2.repository.jdbc.impl;

import com.bhakti.bktapijava2.repository.jdbc.IConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigRepository implements IConfigRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String findMyCompanyServerAddress() {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForObject("SELECT myCompany_Url FROM TblConfig", mapSqlParameterSource,
                (rs, rowNum) -> {
                    return rs.getString("myCompany_Url") != null ? rs.getString("myCompany_Url") : null;
                });
    }

}
