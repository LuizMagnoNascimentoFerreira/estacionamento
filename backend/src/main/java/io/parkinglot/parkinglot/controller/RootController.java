package io.parkinglot.parkinglot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    public String authenticated(){
        return "Autenticado com sucesso.";
    }
}
