package com.capstone.trend.repository;

import com.capstone.trend.domain.Trendscore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrendscoreRepository extends JpaRepository<Trendscore, String> {

    @Query(value = "select * from trend_score limit 10", nativeQuery = true)
    public List<Trendscore> find_all();
}
