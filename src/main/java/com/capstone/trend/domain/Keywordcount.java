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
@Table(name = "keyword_count")
public class Keywordcount {
    @Id
    @Column(name = "keyword")
    private String keyword;

    @Column(name = "ipc_code")
    private String ipc_code;

    @Column(name = "count")
    private Integer count;

    @Column(name = "count_rank")
    private Integer count_rank;

    @Builder
    public Keywordcount(String keyword, String ipc_code, Integer count, Integer count_rank){
        this.keyword = keyword;
        this.ipc_code = ipc_code;
        this.count = count;
        this.count_rank = count_rank;
    }

}
