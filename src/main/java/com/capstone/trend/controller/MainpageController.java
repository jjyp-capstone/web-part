package com.capstone.trend.controller;

import com.capstone.trend.dto.MainpageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainpageController {
    @GetMapping("/test")
    public String getTest(Model model){
        model.addAttribute("keyword",new MainpageDTO());

        return "mainpage";
    }

    @PostMapping("/test")
    public String setTest(@ModelAttribute("keyword") MainpageDTO mainpageDTO){

        return "resultpage";
    }

}
