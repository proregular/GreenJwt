package com.green.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("admin-mentor")
public class AdminMentorController {
    @GetMapping
    public String get() {
        return "admin-mentor";
    }
}