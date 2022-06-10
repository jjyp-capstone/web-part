package com.capstone.trend.controller;

import com.capstone.trend.Crawl;
import com.capstone.trend.YoutubeAPI;
import com.capstone.trend.domain.IPC;
import com.capstone.trend.domain.Keywordcount;
import com.capstone.trend.dto.MainpageDTO;
import com.capstone.trend.dto.YoutubeDTO;
import com.capstone.trend.repository.IPCRepository;
import com.capstone.trend.repository.KeywordcountReopsitory;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainpageController {

    @Autowired
    private final IPCRepository ipcRepository;

    @Autowired
    private final KeywordcountReopsitory keywordcountReopsitory;

    @GetMapping("/main")
    public String getSearch(Model model){

        return "mainpage";
    }
    /** 2022-06-06 윤선희 : keyword로 소스 옮겨둠 필요할때 여기 주석 풀고 keyword쪽 postmapping 주석처리해서 사용하시면됩니다.
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
     */

    //2022-06-06 윤선희
    //탬플릿 추가 
    //18:14 - postsRepository 추가 작업 필요함 지금은 주석처리해둠 
    @GetMapping("/home") //home 화면
    public String home(Model model){

        return "home"; 
 
    }

    @GetMapping("/keyword") //IPC 키워드 화면
    public String keyword(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @RequestParam("param1") String param1, Model model){

        List<Keywordcount> keywordcounts= new ArrayList<>();

        model.addAttribute("param1", param1); //클릭한 IPC값

        //System.out.println("ipc code: "+ipcCode);
        System.out.println("keyword: "+keyword);

        System.out.println("test success");

        keywordcounts.addAll(keywordcountReopsitory.findByIpcCode(param1));
        model.addAttribute("keyword",keywordcounts);

        return "keyword"; 
 
    }

    @PostMapping("/keyword")
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

        //return "resultpage";
        return "keyword_detail";
    }

    @GetMapping("/IPC")
    public String IPC(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                          @RequestParam(value = "ipcCode", required = false, defaultValue = "") String ipcCode, Model model){

        List<IPC> ipcList = new ArrayList<>();

        ipcList.addAll(ipcRepository.find_all());
        model.addAttribute("IPC",ipcList);

        List<Keywordcount> keywordcounts= new ArrayList<>();

        System.out.println("ipc code: "+ipcCode);
        System.out.println("keyword: "+keyword);

        if (keyword.isBlank() && ipcCode.isBlank()){
            keywordcounts.addAll(keywordcountReopsitory.find_all());
            model.addAttribute("keyword",keywordcounts);

        }

        else if(!keyword.isBlank() && ipcCode.isBlank() ){
            System.out.println("search by keyword");
            keywordcounts.addAll(keywordcountReopsitory.findByKeyword(keyword));
            model.addAttribute("keyword",keywordcounts);

        }

        else if(keyword.isBlank() && !ipcCode.isBlank()){
            System.out.println("search by ipc code");
            keywordcounts.addAll(keywordcountReopsitory.findByIpcCode(ipcCode));
            model.addAttribute("keyword",keywordcounts);


        }
        else{
            System.out.println("test success");

            keywordcounts.addAll(keywordcountReopsitory.find_all());
            model.addAttribute("keyword",keywordcounts);

        }

        return "ipcpage";

    }

    @GetMapping("/keyword_detail") //키워드 관련 기사, 유튜브화면 
    public String keyword_detail(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "keyword_detail"; 
 
    }


    @GetMapping("/viewall")
    public String viewAll(Model model){
        //model.addAttribute("list",postsRepository.postall());

        //return "demo_show";
        return "chart01"; //예시 워드 클라우드뷰
    }

    @GetMapping("/A_section") //A_section
    public String A_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "A_section"; 
 
    }

    @GetMapping("/B_section") //B_section
    public String B_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "B_section"; 
 
    }

    @GetMapping("/C_section") //C_section
    public String C_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "C_section"; 
 
    }

    @GetMapping("/D_section") //D_section
    public String D_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "D_section"; 
 
    }

    @GetMapping("/E_section") //E_section
    public String E_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "E_section"; 
 
    }

    @GetMapping("/F_section") //F_section
    public String F_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "F_section"; 
 
    }

    @GetMapping("/G_section") //G_section
    public String G_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "G_section"; 
 
    }

    @GetMapping("/H_section") //H_section
    public String H_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "H_section"; 
 
    }

    @GetMapping("/Y_section") //Y_section
    public String Y_section(Model model){
        //model.addAttribute("list",postsRepository.postall());
        return "Y_section"; 
 
    }

    //2022-06-06 윤선희
    //탬플릿 추가
}
