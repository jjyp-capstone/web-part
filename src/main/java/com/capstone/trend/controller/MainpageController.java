package com.capstone.trend.controller;

import com.capstone.trend.dto.MainpageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainpageController {
    @GetMapping("/main")
    public String getSearch(Model model){
        model.addAttribute("keyword",new MainpageDTO());

        return "mainpage";
    }

    @PostMapping("/main")
    public String setSearch(@ModelAttribute("keyword") MainpageDTO mainpageDTO){

        return "resultpage";
    }

}
