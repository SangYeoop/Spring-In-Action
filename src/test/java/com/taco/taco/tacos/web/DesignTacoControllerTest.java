package com.taco.taco.tacos.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.SynchronousQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DesignTacoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("타코 디자인 뷰")
    @Test
    public void design() throws Exception{
        mockMvc.perform(get("/design"))
                .andExpect(view().name("design"))
                .andExpect(model().attributeExists("wrap"))
                .andExpect(model().attributeExists("protein"))
                .andExpect(model().attributeExists("veggies"))
                .andExpect(model().attributeExists("cheese"))
                .andExpect(model().attributeExists("sauce"))
                .andExpect(status().isOk());
    }

    @DisplayName("타코 디자인 요청 - 성공")
    @Test
    public void requestTacoDesignWithCorrectInput() throws Exception{
        mockMvc.perform(post("/design")
                .param("name", "sangyeop")
                .param("ingredients", "FLTO")
                .param("ingredients", "GRBF")
                .param("ingredients", "TMTO")
                .param("ingredients", "CHED")
                .param("ingredients", "SLSA"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"));
    }

    @DisplayName("타코 디자인 요청 - 실패 (이름 길이 부족)")
    @Test
    public void requestTacoDesignWithFailure() throws Exception{
        mockMvc.perform(post("/design")
                .param("name", "sa")
                .param("ingredients", "FLTO")
                .param("ingredients", "GRBF")
                .param("ingredients", "TMTO")
                .param("ingredients", "CHED")
                .param("ingredients", "SLSA"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }
}
