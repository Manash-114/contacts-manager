package com.smartcontactmanager.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title", "Home!");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model m){
        m.addAttribute("title", "About");
        return "about";
    }
}
