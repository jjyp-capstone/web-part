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
@Table(name = "ipc_title")
public class IPCtitle {

    @Id
    @Column(name = "key_id")
    private Integer key_id;

    @Column(name = "ipc_code")
    private String ipc_code;

    @Column(name = "title")
    private String title;

    @Builder
    public IPCtitle(Integer key_id, String ipc_code, String title){
        this.key_id = key_id;
        this.ipc_code = ipc_code;
        this.title = title;
    }
}
