package com.kata.kata.batch;

import com.kata.kata.service.KataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class KataBatch implements CommandLineRunner {

    private final KataService kataService;
    private final ApplicationContext context;


    @Autowired
    public KataBatch(KataService kataService, ApplicationContext context) {
        this.kataService = kataService;
        this.context = context;
    }

    @Override
    public void run(String... args) {
        // intercept si le batch à été appelé
        if (args.length == 2 && "--batch".equals(args[0])) {
            String inputFilePath = args[1];
            // appel du fun processFile
            processFile(inputFilePath);
            // quitter l'application
            SpringApplication.exit(context, () -> 0);
        }
    }

    private void processFile(String inputFilePath) {
        Path input = Paths.get(inputFilePath);

        // vérification si le fichier existe
        if (!Files.exists(input)) {
            System.err.println(" Fichier introuvable à : " + input.toAbsolutePath());
            return;
        }

        // le fichier de sortie avec le suffixe _output
        String outputFileName = input.getFileName().toString().replace(".txt", "") + "_output.txt";
        Path output = input.getParent().resolve(outputFileName);

        List<String> results = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(input)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        int number = Integer.parseInt(line);
                        if (number >= 0 && number <= 100) {
                            String transformed = kataService.transform(number);
                            results.add(number + " \"" + transformed + "\"");
                        } else {
                            results.add(line + " \"Erreur: nombre hors limites (0-100)\"");
                        }
                    } catch (NumberFormatException e) {
                        results.add(line + " \"Erreur: format de nombre invalide\"");
                    }
                }
            }

            // insertion résultats dans le fichier de sortie
            try (BufferedWriter writer = Files.newBufferedWriter(output)) {
                for (String result : results) {
                    writer.write(result);
                    writer.newLine();
                }
            }


        } catch (IOException e) {
            System.err.println("Erreur lors du traitement du fichier : " + e.getMessage());
        }
    }

}
