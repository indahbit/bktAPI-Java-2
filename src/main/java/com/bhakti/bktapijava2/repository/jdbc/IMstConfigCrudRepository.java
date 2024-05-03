package com.bhakti.bktapijava2.repository.jdbc;

import com.bhakti.bktapijava2.entity.MstConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMstConfigCrudRepository extends PagingAndSortingRepository<MstConfig, String>, CrudRepository<MstConfig, String> {

    Optional<MstConfig> findByConfigNameAndConfigType(String configName, String configType);
}
