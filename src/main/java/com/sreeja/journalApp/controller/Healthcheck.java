package com.sreeja.journalApp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class Healthcheck {
    
    @GetMapping("/health-check")
    public String healthcheck(){
        /*method created */
        return "OK";
    }

}
