package com.MediCare.MatiasBarrantes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "access-denied";
    }
}
