package com.bookmanage.controller;

import com.bookmanage.common.JwtUtil;
import com.bookmanage.common.Result;
import com.bookmanage.dto.LoginRequest;
import com.bookmanage.dto.LoginResponse;
import com.bookmanage.dto.RegisterRequest;
import com.bookmanage.entity.User;
import com.bookmanage.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * 登录注册接口
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * 注册新用户，自动设为普通 user 角色
     */
    @PostMapping("/register")
    public Result<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (userMapper.selectByUsername(request.getUsername()) != null) {
            return Result.error(400, "用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("user");
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        return Result.success(new LoginResponse(token, user.getUsername(), user.getRole()));
    }

    /**
     * 登录，校验账号密码，返回 JWT Token
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userMapper.selectByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Result.error(401, "用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        return Result.success(new LoginResponse(token, user.getUsername(), user.getRole()));
    }
}
