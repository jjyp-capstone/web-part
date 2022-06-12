package com.capstone.trend.repository;

import com.capstone.trend.domain.IPCtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPCtitleRepository extends JpaRepository<IPCtitle, Integer> {

    @Query(value = "select * from ipc_title", nativeQuery = true)
    List<IPCtitle> find_all();

    @Query(value = "select * from ipc_title where ipc_code = :ipcCode", nativeQuery = true)
    List<IPCtitle> findByCode(@Param("ipcCode")String ipcCode);
}
