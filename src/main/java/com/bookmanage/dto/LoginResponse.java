package com.bookmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录响应体，返回 JWT Token 和用户信息
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String role;
}
