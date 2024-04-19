package com.bhakti.bktapijava2.repository.jdbc;

import com.bhakti.bktapijava2.entity.CofEmailJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICofEmailJobCrudRepository extends PagingAndSortingRepository<CofEmailJob, String>, CrudRepository<CofEmailJob, String> {

    Optional<CofEmailJob> findByNamaJob(String namaJob);

}
