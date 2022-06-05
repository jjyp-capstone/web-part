package com.capstone.trend.controller;

import com.capstone.trend.Crawl;
import com.capstone.trend.YoutubeAPI;
import com.capstone.trend.dto.MainpageDTO;
import com.capstone.trend.dto.YoutubeDTO;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainpageController {
    @GetMapping("/main")
    public String getSearch(Model model){

        return "mainpage";
    }

    @PostMapping("/main")
    public String setSearch(@ModelAttribute("mainpage") MainpageDTO mainpageDTO){
        List<String> crawl_result = Crawl.main(mainpageDTO.getKeyword());
        mainpageDTO.setNewsURL(crawl_result.get(0));
        mainpageDTO.setNewstitle(crawl_result.get(1));

        // youtube id를 배열에 담아놓았습니다.
        List<String> youtube_id = YoutubeAPI.getVideoId(mainpageDTO.getKeyword());
        List<String> youtube_title = YoutubeAPI.getVideotitle(mainpageDTO.getKeyword());
        mainpageDTO.setYoutubeid(youtube_id);
        mainpageDTO.setYoutubetitle(youtube_title);
        System.out.println(youtube_id);
        System.out.println(youtube_title);

        return "resultpage";
    }

}
