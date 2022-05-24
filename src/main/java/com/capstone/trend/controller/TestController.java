package com.capstone.trend.controller;

import com.capstone.trend.dto.FormDto;
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
    public String showForm(Model model){
        model.addAttribute("form", new FormDto());
        return "review/form";
    }

    @PostMapping("/form")
    public String form(@ModelAttribute("form") FormDto formDto){

        return "review/form";
    }
}
