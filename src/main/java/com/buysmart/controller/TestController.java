package com.buysmart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // Simple test endpoint
    @GetMapping("/test")
    public String testEndpoint() {
        return "Backend is up and running!";
    }
}
