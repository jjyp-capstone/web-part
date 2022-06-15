package com.capstone.trend.controller;

import com.capstone.trend.Crawl;
import com.capstone.trend.Navernews;
import com.capstone.trend.YoutubeAPI;
import com.capstone.trend.domain.*;
import com.capstone.trend.dto.FormDTO;
import com.capstone.trend.dto.MainpageDTO;
import com.capstone.trend.dto.NavernewsDTO;
import com.capstone.trend.dto.YoutubeDTO;
import com.capstone.trend.repository.*;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainpageController {

    @Autowired
    private final IPCRepository ipcRepository;

    @Autowired
    private final KeywordcountReopsitory keywordcountReopsitory;

    @Autowired
    private final IPChistoryRepository ipchistoryRepository;

    @Autowired
    private final TrendscoreRepository trendscoreRepository;

    @GetMapping("/main")
    public String getSearch(Model model) {

        return "mainpage";
    }

    /**
     * 2022-06-06 윤선희 : keyword로 소스 옮겨둠 필요할때 여기 주석 풀고 keyword쪽 postmapping
     * 
     * 
     * 석처리해서
     * 사용하시면됩니다.
     * @PostMapping("/main")
     * public String setSearch(@ModelAttribute("mainpage") MainpageDTO mainpageDTO){
     * List<String> crawl_result = Crawl.main(mainpageDTO.getKeyword());
     * mainpageDTO.setNewsURL(crawl_result.get(0));
     * mainpageDTO.setNewstitle(crawl_result.get(1));
     * 
     * // youtube id를 배열에 담아놓았습니다.
     * List<String> youtube_id = YoutubeAPI.getVideoId(mainpageDTO.getKeyword());
     * List<String> youtube_title =
     * YoutubeAPI.getVideotitle(mainpageDTO.getKeyword());
     * mainpageDTO.setYoutubeid(youtube_id);
     * mainpageDTO.setYoutubetitle(youtube_title);
     * System.out.println(youtube_id);
     * System.out.println(youtube_title);
     * 
     * return "resultpage";
     * }
     */

    // 2022-06-06 윤선희
    // 탬플릿 추가
    // 18:14 - postsRepository 추가 작업 필요함 지금은 주석처리해둠
    @GetMapping("/home") // home 화면
    public String home(@RequestParam(value = "ipctitle", required = false, defaultValue = "") String ipctitle,
            @RequestParam(value = "ipcCode", required = false, defaultValue = "") String ipcCode,
            @RequestParam(value = "frequency", required = false, defaultValue = "") String frequency,
            @RequestParam(value = "average", required = false, defaultValue = "") String average,
            @RequestParam(value = "trend", required = false, defaultValue = "") String trend, Model model) {

        List<IPCtitle> ipCtitles = new ArrayList<>();
        ipCtitles.addAll(ipCtitleRepository.find_all());
        model.addAttribute("ipctitle", ipCtitles);

        List<IPC> ipcList = new ArrayList<>();
        ipcList.addAll(ipcRepository.find_all());
        model.addAttribute("IPC", ipcList);

        List<IPC> ipc_freq = new ArrayList<>();
        ipc_freq.addAll(ipcRepository.find_top10());
        model.addAttribute("IPC_freq", ipc_freq);

        List<IPC> ipc_aver = new ArrayList<>();
        ipc_aver.addAll(ipcRepository.find_top10_B());
        model.addAttribute("IPC_aver", ipc_aver);

        List<Trendscore> trendscores = new ArrayList<>();
        trendscores.addAll(trendscoreRepository.find_all());
        model.addAttribute("top_trend", trendscores);

        return "home";

    }

    @GetMapping("/keyword") // IPC 키워드 화면
    public String keyword(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam("param1") String param1, Model model) {

        List<Keywordcount> keywordcounts = new ArrayList<>();

        model.addAttribute("param1", param1); // 클릭한 IPC값

        keywordcounts.addAll(keywordcountReopsitory.findByIpcCode(param1));
        model.addAttribute("keyword", keywordcounts);

        List<Organization> organizations = new ArrayList<>();
        organizations.addAll(organizationRepository.findByCode(param1));
        model.addAttribute("organization", organizations);
        System.out.println("organization success");

        List<IPChistory> ipchistoryList = new ArrayList<>();
        ipchistoryList.addAll(ipchistoryRepository.findByCode(param1));
        model.addAttribute("ipc_history", ipchistoryList);
        System.out.println("ipc_history success");

        return "keyword";

    }

    @PostMapping("/keyword")
    public String keyword(@ModelAttribute(value = "query") FormDTO formDTO, Model model) {
        List<NavernewsDTO> navernewsDTOList = new ArrayList<>();

        JSONArray items = Navernews.getJson(formDTO.getKeyword());

        for (int i = 0; i < items.length(); i++) {

            NavernewsDTO navernewsDTO = new NavernewsDTO();
            JSONObject obj = items.getJSONObject(i);
            String title = obj.getString("title");
            String link = obj.getString("link");

            String paresed_title = Jsoup.parse(title).text();

            navernewsDTO.setTitle(paresed_title);
            navernewsDTO.setLink(link);

            navernewsDTOList.add(navernewsDTO);

            System.out.println(paresed_title);
            System.out.println(link);
        }

        model.addAttribute("news", navernewsDTOList);

        String kw = formDTO.getKeyword();

        Iterator<SearchResult> searchResultIterator = YoutubeAPI.getVideoId(kw);
        List<YoutubeDTO> youtubeDTOList = new ArrayList<>();

        while (searchResultIterator.hasNext()) {

            YoutubeDTO youtubeDTO = new YoutubeDTO();
            youtubeDTO.setKeyword(kw);
            SearchResult singleVideo = searchResultIterator.next();
            ResourceId rId = singleVideo.getId();
            youtubeDTO.setVideoId(rId.getVideoId());
            youtubeDTO.setTitle(singleVideo.getSnippet().getTitle());
            Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
            youtubeDTO.setThumbnailPath(thumbnail.getUrl());

            youtubeDTOList.add(youtubeDTO);
        }

        model.addAttribute("youtubes", youtubeDTOList);

        return "keyword_detail";
    }

    @GetMapping("/keyword_detail") // 키워드 관련 기사, 유튜브화면
    public String keyword_detail(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "keyword_detail";

    }

    @GetMapping("/IPC")
    public String IPC(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "ipcCode", required = false, defaultValue = "") String ipcCode, Model model) {

        List<IPC> ipcList = new ArrayList<>();

        ipcList.addAll(ipcRepository.find_all());
        model.addAttribute("IPC", ipcList);

        List<Keywordcount> keywordcounts = new ArrayList<>();

        System.out.println("ipc code: " + ipcCode);
        System.out.println("keyword: " + keyword);

        if (keyword.isBlank() && ipcCode.isBlank()) {
            keywordcounts.addAll(keywordcountReopsitory.find_all());
            model.addAttribute("keyword", keywordcounts);

        }

        else if (!keyword.isBlank() && ipcCode.isBlank()) {
            System.out.println("search by keyword");
            keywordcounts.addAll(keywordcountReopsitory.findByKeyword(keyword));
            model.addAttribute("keyword", keywordcounts);

        }

        else if (keyword.isBlank() && !ipcCode.isBlank()) {
            System.out.println("search by ipc code");
            keywordcounts.addAll(keywordcountReopsitory.findByIpcCode(ipcCode));
            model.addAttribute("keyword", keywordcounts);

        } else {
            System.out.println("test success");

            keywordcounts.addAll(keywordcountReopsitory.find_all());
            model.addAttribute("keyword", keywordcounts);

        }

        return "ipcpage";

    }

    @Autowired
    private final OrganizationRepository organizationRepository;

    @GetMapping("/organization")
    public String organization(@RequestParam(value = "ipcCode", required = false, defaultValue = "") String ipcCode,
            Model model) {
        List<Organization> organizations = new ArrayList<>();
        organizations.addAll(organizationRepository.findByCode(ipcCode));
        model.addAttribute("organization", organizations);

        return "testpage2";

    }

    @Autowired
    private final IPCtitleRepository ipCtitleRepository;

    @GetMapping("/ipctitle")
    public String ipctitle(@RequestParam(value = "ipcCode", required = false, defaultValue = "") String ipcCode,
            Model model) {
        List<IPCtitle> ipCtitles = new ArrayList<>();
        ipCtitles.addAll(ipCtitleRepository.findByCode(ipcCode));
        model.addAttribute("ipctitle", ipCtitles);

        return "ipc_title";
    }

    @GetMapping("top_trend")
    public String top_trend(Model model) {
        List<Trendscore> trendscores = new ArrayList<>();

        trendscores.addAll(trendscoreRepository.find_all());
        model.addAttribute("top_trend", trendscores);

        return "top_trend";
    }

    //////// youtube 검색 부분 추가

    @GetMapping("/youtube_search")
    public String youtubesearch(@ModelAttribute(value = "query") FormDTO formDTO) {

        return "youtube_search";
    }

    @PostMapping("/youtube_result")
    public String youtuberesult(@ModelAttribute(value = "query") FormDTO formDTO, Model model) {

        String kw = formDTO.getKeyword();

        Iterator<SearchResult> searchResultIterator = YoutubeAPI.getVideoId(kw);
        List<YoutubeDTO> youtubeDTOList = new ArrayList<>();

        while (searchResultIterator.hasNext()) {

            YoutubeDTO youtubeDTO = new YoutubeDTO();
            youtubeDTO.setKeyword(kw);
            SearchResult singleVideo = searchResultIterator.next();
            ResourceId rId = singleVideo.getId();
            youtubeDTO.setVideoId(rId.getVideoId());
            youtubeDTO.setTitle(singleVideo.getSnippet().getTitle());
            Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
            youtubeDTO.setThumbnailPath(thumbnail.getUrl());

            youtubeDTOList.add(youtubeDTO);
        }

        model.addAttribute("youtubes", youtubeDTOList);

        return "youtube_result";
    }

    // News 검색 부분 추가
    @GetMapping("/news_search")
    public String news_search(Model model) {

        model.addAttribute("query", new FormDTO());

        return "news_search";
    }

    @PostMapping("/news_result")
    public String news_result(@ModelAttribute(value = "query") FormDTO formDTO, Model model) {
        List<NavernewsDTO> navernewsDTOList = new ArrayList<>();

        JSONArray items = Navernews.getJson(formDTO.getKeyword());

        for (int i = 0; i < items.length(); i++) {

            NavernewsDTO navernewsDTO = new NavernewsDTO();
            JSONObject obj = items.getJSONObject(i);
            String title = obj.getString("title");

            String paresed_title = Jsoup.parse(title).text();
            String link = obj.getString("link");

            navernewsDTO.setTitle(paresed_title);
            navernewsDTO.setLink(link);

            navernewsDTOList.add(navernewsDTO);

            System.out.println(paresed_title);
            System.out.println(link);
        }

        model.addAttribute("news", navernewsDTOList);

        return "news_result";
    }

    // 꺾은선 그래프용 데이터

    @GetMapping("ipc_history")
    public String ipc_history(@RequestParam(value = "ipcCode") String ipcCode, Model model) {
        List<IPChistory> ipchistoryList = new ArrayList<>();

        ipchistoryList.addAll(ipchistoryRepository.findByCode(ipcCode));
        model.addAttribute("ipc_history", ipchistoryList);
        model.addAttribute("ipc_code", ipcCode);

        return "ipc_history";
    }

    // 사이드 메뉴 (IPC section 부분)
    @GetMapping("/A_section") // A_section
    public String A_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "A_section";

    }

    @GetMapping("/B_section") // B_section
    public String B_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "B_section";

    }

    @GetMapping("/C_section") // C_section
    public String C_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "C_section";

    }

    @GetMapping("/D_section") // D_section
    public String D_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "D_section";

    }

    @GetMapping("/E_section") // E_section
    public String E_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "E_section";

    }

    @GetMapping("/F_section") // F_section
    public String F_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "F_section";

    }

    @GetMapping("/G_section") // G_section
    public String G_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "G_section";

    }

    @GetMapping("/H_section") // H_section
    public String H_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "H_section";

    }

    @GetMapping("/Y_section") // Y_section
    public String Y_section(Model model) {
        // model.addAttribute("list",postsRepository.postall());
        return "Y_section";

    }

    // 2022-06-06 윤선희
    // 탬플릿 추가
}
