package com.capstone.trend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class YoutubeDTO {
    private String title;
    private String thumbnailPath;
    private String videoId;

    @Builder(toBuilder = true)
    public YoutubeDTO(String title, String thumbnailPath, String videoId){
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.videoId = videoId;
    }

}
