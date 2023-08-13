package com.matrix.projectboard.controller;

import com.matrix.projectboard.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * author         : Jason Lee
 * date           : 2023-08-10
 * description    :
 */
@Import(SecurityConfig.class)
@WebMvcTest(MainController.class) // Bean Scanning 최소화
class MainControllerTest {

    private final MockMvc mvc;

    public MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void giveNothing_whenRequestingRootPage_thenRedirectsToArticlesPage_test() throws Exception {
        // given

        // when & Then
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());

        // then
    }
}
