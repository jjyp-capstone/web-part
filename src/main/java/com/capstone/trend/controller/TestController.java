package com.capstone.trend.controller;

import com.capstone.trend.dto.TestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/form")
    public String get(Model model){
        model.addAttribute("form", new TestDto());
        return "review/test";
    }

    @PostMapping("/form")
    public String post(@ModelAttribute("form") String teststring){

        return "review/test";
    }
}
