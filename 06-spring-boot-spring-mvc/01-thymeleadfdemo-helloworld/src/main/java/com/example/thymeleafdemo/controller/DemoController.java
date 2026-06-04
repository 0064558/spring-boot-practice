package com.example.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
public class DemoController {
    // create a mapping for "/hello"
    @GetMapping("/hello")
    public String helloWorld(Model model) {
        model.addAttribute("date", LocalDateTime.now());

        return "helloworld";
    }
}
