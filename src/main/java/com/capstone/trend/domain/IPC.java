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
@Table(name = "IPC_List")
public class IPC {

    @Id
    @Column(name = "ipc_code")
    private String ipc_code;
    @Column(name = "frequency")
    private Float frequency;
    @Column(name = "average_fluctuation_rate")
    private Float average_fluctuation_rate;

    @Builder
    public IPC(String ipc_code, Float frequency, Float average_fluctuation_rate){
        this.ipc_code = ipc_code;
        this.frequency = frequency;
        this.average_fluctuation_rate = average_fluctuation_rate;
    }

}
