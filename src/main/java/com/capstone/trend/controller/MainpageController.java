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

    //2022-06-06 윤선희
    //탬플릿 추가 
    //18:14 - postsRepository 추가 작업 필요함 지금 오류로 나타남 
    /**
    @GetMapping("/viewall")
    public String viewAll(Model model){
        model.addAttribute("list",postsRepository.postall());

        //return "demo_show";
        return "chart01"; //예시 워드 클라우드뷰
    }

    @GetMapping("/home") //home 화면
    public String home(Model model){
        model.addAttribute("list",postsRepository.postall());

        //return "demo_show";
        return "home"; 
 
    }

    @GetMapping("/keyword") //IPC 키워드 화면
    public String keyword(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "keyword"; 
 
    }

    @GetMapping("/keyword_detail") //키워드 관련 기사, 유툽화면 
    public String keyword_detail(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "keyword_detail"; 
 
    }

    @GetMapping("/A_section") //A_section
    public String A_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "A_section"; 
 
    }

    @GetMapping("/B_section") //B_section
    public String B_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "B_section"; 
 
    }

    @GetMapping("/C_section") //C_section
    public String C_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "C_section"; 
 
    }

    @GetMapping("/D_section") //D_section
    public String D_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "D_section"; 
 
    }

    @GetMapping("/E_section") //E_section
    public String E_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "E_section"; 
 
    }

    @GetMapping("/F_section") //F_section
    public String F_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "F_section"; 
 
    }

    @GetMapping("/G_section") //G_section
    public String G_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "G_section"; 
 
    }

    @GetMapping("/H_section") //H_section
    public String H_section(Model model){
        model.addAttribute("list",postsRepository.postall());
        return "H_section"; 
 
    }

    @GetMapping("/Y_section") //Y_section
    public String Y_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "Y_section"; 
 
    }
     */
    //2022-06-06 윤선희
    //탬플릿 추가
}
