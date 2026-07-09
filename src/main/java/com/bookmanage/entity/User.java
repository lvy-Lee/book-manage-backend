package com.bookmanage.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体，对应数据库 user 表
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createTime;
}
