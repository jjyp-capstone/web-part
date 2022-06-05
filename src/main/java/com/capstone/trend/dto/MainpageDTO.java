package com.capstone.trend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainpageDTO {
    private String keyword;
    private String newsURL;
    private String newstitle;
    private List<String> youtubeid;
    private List<String> youtubetitle;
}