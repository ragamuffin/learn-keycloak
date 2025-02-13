package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello!";
    }

    @GetMapping("/bye")
    public String sayBye() {
        return "Bye!";
    }
}
