package com.bookmanage.mapper;

import com.bookmanage.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库操作
 */
@Mapper
public interface UserMapper {
    User selectByUsername(String username);

    User selectById(Long id);

    int insert(User user);
}
