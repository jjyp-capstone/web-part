package com.capstone.trend.domain;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "ipc_history")
public class IPChistory {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="_date")
    private String _date;

    @Column(name="count")
    private Integer count;

}
