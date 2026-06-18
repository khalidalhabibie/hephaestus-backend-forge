package com.fif.training.exercisespringboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Root {

    @GetMapping
    public String checkAPI() {
        return "API is Running!";
    }
}
