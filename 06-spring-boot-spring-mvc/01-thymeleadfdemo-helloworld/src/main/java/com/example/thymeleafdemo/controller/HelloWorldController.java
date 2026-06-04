package com.example.thymeleafdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    @GetMapping("/showForm")
    public String showHelloForm() {
        return "helloworld-form";
    }

    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld";
    }

    // need a controller method to read form data and
    // add data to the model

    @RequestMapping("/processFormVersionTwo")
    public String letShoutDude (HttpServletRequest request, Model model) {

        // read the request parameter from the HTML form
        String name = request.getParameter("studentName");

        // convert dhe data to all caps
        name.toUpperCase();

        // create a message
        String result = "Yo! " + name;

        // add message to the model
        model.addAttribute("message", result);

        return "helloworld";
    }

    @RequestMapping("/processFormVersionThree")
    public String processFormVersionThree (@RequestParam("studentName") String name, Model model) {

        // convert dhe data to all caps
        name.toUpperCase();

        // create a message
        String result = "Hey My Friend from v3! " + name;

        // add message to the model
        model.addAttribute("message", result);

        return "helloworld";
    }
}
