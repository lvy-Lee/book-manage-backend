package com.bookmanage.mapper;

import com.bookmanage.entity.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 借阅记录数据库操作
 */
@Mapper
public interface BorrowRecordMapper {
    int insert(BorrowRecord record);

    BorrowRecord selectById(Long id);

    /**
     * 查询当前用户的借阅记录（关联图书名称）
     */
    List<com.bookmanage.dto.BorrowRecordResponse> selectByUserId(@Param("userId") Long userId);

    /**
     * 查询全部借阅记录（关联图书名称和用户名），管理员用
     */
    List<com.bookmanage.dto.BorrowRecordResponse> selectAllWithUser();

    /**
     * 更新记录（还书时修改 return_time 和 status）
     */
    int updateById(BorrowRecord record);
}
