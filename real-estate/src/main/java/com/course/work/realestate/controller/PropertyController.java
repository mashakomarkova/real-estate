package com.course.work.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PropertyController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
