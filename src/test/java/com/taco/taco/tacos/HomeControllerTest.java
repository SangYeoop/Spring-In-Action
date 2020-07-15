package com.taco.taco.tacos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Home 뷰 보이는지 테스트")
    @Test
    void homePage() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to...")));
    }
}