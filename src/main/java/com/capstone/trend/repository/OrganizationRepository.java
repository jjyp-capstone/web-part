package com.capstone.trend.repository;

import com.capstone.trend.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "select organ_id,organization,patent_number,ipc_code from organization_order where ipc_code=:ipcCode limit 10", nativeQuery = true)
    List<Organization> findByCode(@Param("ipcCode") String ipcCode);
}
