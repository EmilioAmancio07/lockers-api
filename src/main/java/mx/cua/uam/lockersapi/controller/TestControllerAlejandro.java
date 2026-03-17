package mx.cua.uam.lockersapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestControllerAlejandro {

    @GetMapping("/alejandro")
    public String holaMundo() {
        return "Hola Mundo - Alejandro Ramos.";
    }
}