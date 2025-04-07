package com.kata.kata.controller;

import com.kata.kata.service.KataService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foobarquix")
public class KataController {

    private final KataService kataService;

    @Autowired
    public KataController(KataService kataService) {
        this.kataService = kataService;
    }

    @GetMapping("/{number}")
    public ResponseEntity<String> transformNumber(
            @PathVariable
            @Min(value = 0, message = "Le nombre doit être supérieur ou égal à 0")
            @Max(value = 100, message = "Le nombre doit être inférieur ou égal à 100")
            int number ){
        String result = kataService.transform(number);
        return ResponseEntity.ok(result);
    }
}
