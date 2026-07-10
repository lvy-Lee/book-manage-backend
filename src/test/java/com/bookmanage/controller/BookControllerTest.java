package com.bookmanage.controller;

import com.bookmanage.common.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 图书管理接口测试
 */
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        adminToken = jwtUtil.generateToken(1L, "admin", "admin");
        userToken = jwtUtil.generateToken(2L, "user", "user");
    }

    @Test
    void listBooks_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/books")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.total").isNumber())
                .andExpect(jsonPath("$.data.page").value(1));
    }

    @Test
    void adminCreateBook_shouldSucceed() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "测试图书");
        body.put("author", "测试作者");
        body.put("category", "计算机");

        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value("测试图书"));
    }

    @Test
    void userCreateBook_shouldReturn403() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "测试图书");
        body.put("author", "测试作者");

        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    void getBookById_notFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/books/999")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void withoutToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().is(401));
    }
}
