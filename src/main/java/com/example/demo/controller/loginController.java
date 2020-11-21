package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class loginController {

    @PostMapping
    @CrossOrigin
    public UUID login() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        return uuid;
    }
}
