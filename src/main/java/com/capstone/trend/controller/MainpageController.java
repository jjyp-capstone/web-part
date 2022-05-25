package com.capstone.trend.controller;

import com.capstone.trend.Crawl;
import com.capstone.trend.dto.MainpageDTO;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainpageController {
    @GetMapping("/main")
    public String getSearch(Model model){

        return "mainpage";
    }

    @PostMapping("/main")
    public String setSearch(@ModelAttribute("keyword") MainpageDTO mainpageDTO){
        String[] crawl_result = Crawl.main(mainpageDTO.getKeyword());
        mainpageDTO.setURL(crawl_result[0]);
        mainpageDTO.setTitle(crawl_result[1]);
        return "resultpage";
    }

}
