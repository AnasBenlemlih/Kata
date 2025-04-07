package com.kata.kata.controller;


import com.kata.kata.service.KataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KataController.class)
public class KataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KataService service;

    @Test
    void shouldReturnTransformedNumber() throws Exception {
        when(service.transform(3)).thenReturn("FOOFOO");
        mockMvc.perform(get("/api/foobarquix/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("FOOFOO"));
    }

    @Test
    void shouldReturnBadRequestForInvalidRange() throws Exception {
        mockMvc.perform(get("/api/foobarquix/101"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/foobarquix/-1"))
                .andExpect(status().isBadRequest());
    }
}
