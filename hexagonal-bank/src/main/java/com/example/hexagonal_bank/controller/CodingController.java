package com.example.hexagonal_bank.controller;

import com.example.hexagonal_bank.services.CodingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api-coding")
public class CodingController {

    private CodingService codingService;

    public CodingController(CodingService codingService){
        this.codingService = codingService;
    }

    @GetMapping("/test")
    public void firstMethod(){
        this.codingService.testMain();
    }
}
