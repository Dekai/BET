package com.dk.alirr.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dk")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Good luck Dekai!";
    }
}
