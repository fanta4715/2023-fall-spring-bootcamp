package com.example.fisrtproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello2Controller {

    @GetMapping("/hello2")
    public String hello2(){
        return "hello2.html";
    }
}
