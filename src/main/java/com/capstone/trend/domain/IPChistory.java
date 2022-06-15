package com.capstone.trend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ipc_history")
public class IPChistory {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "count")
    private Integer count;

    @Column(name = "ipc_code")
    private String ipc_code;

}
