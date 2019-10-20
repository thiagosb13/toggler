package com.thiagobezerra.toggler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToggleController {
    @GetMapping("/test")
    public String test() {
        return "Ok";
    }
}
