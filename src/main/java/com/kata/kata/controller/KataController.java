package com.kata.kata.controller;

import com.kata.kata.service.KataService;
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
            int number ){
        String result = kataService.transform(number);
        return ResponseEntity.ok(result);
    }
}
