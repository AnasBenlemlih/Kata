package com.kata.kata.batch;

import com.kata.kata.service.KataService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KataBatchTest {

    private KataService kataService;
    private ApplicationContext context;
    private KataBatch kataBatch;

    @BeforeEach
    void setUp() {
        kataService = mock(KataService.class);
        context = mock(ApplicationContext.class);
        kataBatch = new KataBatch(kataService, context);
    }

    @Test
    void testRun_withWrongArgs_doesNothing() {
        kataBatch.run("wrongArg", "file.txt");
        verifyNoInteractions(kataService);
    }

    @Test
    void testRun_withBatchArg_callsProcessFileAndExits() {
        KataBatch spyBatch = Mockito.spy(kataBatch);
        doNothing().when(spyBatch).processFile(anyString());

        spyBatch.run("--batch", "somefile.txt");

        verify(spyBatch).processFile("somefile.txt");
    }

    @Test
    void testProcessFile_validFile() throws IOException {
        // Préparation fichier d’entrée temporaire
        Path tempDir = Files.createTempDirectory("kata_test_");
        Path input = tempDir.resolve("test.txt");
        Files.write(input, List.of("3", "abc", "150", "", "42"));

        when(kataService.transform(3)).thenReturn("Fizz");
        when(kataService.transform(42)).thenReturn("Buzz");

        kataBatch.run("--batch", input.toString());

        Path output = tempDir.resolve("test_output.txt");
        assertTrue(Files.exists(output));

        List<String> outputLines = Files.readAllLines(output);
        assertEquals(4, outputLines.size());

        assertEquals("3 \"Fizz\"", outputLines.get(0));
        assertEquals("abc \"Erreur: format de nombre invalide\"", outputLines.get(1));
        assertEquals("150 \"Erreur: nombre hors limites (0-100)\"", outputLines.get(2));
        assertEquals("42 \"Buzz\"", outputLines.get(3));
    }

    @Test
    void testProcessFile_fileNotFound() {
        Path fakePath = Paths.get("does_not_exist.txt");
        kataBatch.run("--batch", fakePath.toString());
        // Pas d’exception levée → OK
    }
}
