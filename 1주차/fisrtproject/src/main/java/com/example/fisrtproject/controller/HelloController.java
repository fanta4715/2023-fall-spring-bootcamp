package com.example.fisrtproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/controllerTest")
    public String hello(){
        return "hello.html";
    }

    @GetMapping("/myName")
    public String name(Model model){
        model.addAttribute("yourName","이재현");
        return "index";
    }
}
