package com.kata.kata.service;

import org.springframework.stereotype.Service;

@Service
public class KataService {

    private static final String FOO = "FOO";
    private static final String BAR = "BAR";
    private static final String QUIX = "QUIX";

    public String transform(int number){

        // premier règle le nombre doit etre entre 0 et 100
        if (number < 0 || number > 100){
            throw new IllegalArgumentException("Le nombre doit etre 0 et 100");
        }

        StringBuilder result = new StringBuilder();

        // règles en priorité
        if (number % 3 == 0) result.append(FOO);
        if (number % 5 == 0) result.append(BAR);

        // règle si contient un chiffre

        String numberAsString = String.valueOf(number);

        for (char x : numberAsString.toCharArray()) {
            switch (x) {
                case '3' -> result.append(FOO);
                case '5' -> result.append(BAR);
                case '7' -> result.append(QUIX);
                default -> {
                    //  rien à faire
                }
            }
        }

        // cas de aucune règle n'est vérifiee.
        if (result.length() == 0) {
            result.append(number);
        }

        return result.toString();

    }
}
