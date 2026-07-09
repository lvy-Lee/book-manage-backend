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
 * 借阅记录接口测试
 */
@SpringBootTest
@AutoConfigureMockMvc
class BorrowRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() throws Exception {
        adminToken = jwtUtil.generateToken(1L, "admin", "admin");
        userToken = jwtUtil.generateToken(2L, "user", "user");

        // 先创建一本测试图书
        Map<String, Object> book = new HashMap<>();
        book.put("title", "可借的书");
        book.put("author", "作者");
        book.put("category", "文学");

        String json = mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void borrowBook_shouldSucceed() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("bookId", 1);

        mockMvc.perform(post("/api/borrow")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value("borrowed"));
    }

    @Test
    void borrowAlreadyBorrowedBook_shouldReturn400() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("bookId", 1);

        // 先借走
        mockMvc.perform(post("/api/borrow")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        // 再借应该失败
        mockMvc.perform(post("/api/borrow")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("图书已被借出"));
    }

    @Test
    void returnBook_shouldSucceed() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("bookId", 1);

        // 先借书
        mockMvc.perform(post("/api/borrow")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)));

        // 还书
        mockMvc.perform(put("/api/borrow/1/return")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value("returned"));
    }

    @Test
    void getMyRecords_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/borrow/my")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void getAllRecords_asUser_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/borrow/all")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    void getAllRecords_asAdmin_shouldSucceed() throws Exception {
        mockMvc.perform(get("/api/borrow/all")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }
}
