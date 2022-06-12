package com.capstone.trend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "trend_score")
public class Trendscore {

    @Id
    @Column(name = "ipc_code")
    private String ipc_code;

    @Column(name = "trend_score")
    private Double trend_score;

    @Builder
    public Trendscore(String ipc_code, Double trend_score){
        this.ipc_code = ipc_code;
        this.trend_score = trend_score;
    }
}
