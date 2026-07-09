# 图书管理系统 - 后端

基于 Spring Boot 2.7.18 + MyBatis + MySQL 的图书管理系统后端。

## 技术栈

- **框架**: Spring Boot 2.7.18
- **ORM**: MyBatis 2.3.0
- **数据库**: MySQL 8.0 (开发) / H2 (测试)
- **认证**: JWT + BCrypt
- **JDK**: 1.8

## 快速启动

1. 创建 MySQL 数据库 `bookmanage`
2. 修改 `application.properties` 中的数据库密码
3. 运行 `mvnw spring-boot:run`
4. 访问 `http://localhost:8080`

## 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |
| user | user123 | 普通用户 |