package com.capstone.trend.repository;

import com.capstone.trend.domain.IPChistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPChistoryRepository extends JpaRepository<IPChistory, Long> {

    @Query(value = "select id, date, count, ipc_code from ipc_history where ipc_code =:ipcCode", nativeQuery = true)
    List<IPChistory> findByCode(@Param("ipcCode") String ipcCode);
}
