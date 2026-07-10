package com.bookmanage.mapper;

import com.bookmanage.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图书数据库操作
 */
@Mapper
public interface BookMapper {
    List<Book> selectAll();

    List<Book> selectByKeyword(@Param("keyword") String keyword);

    Book selectById(Long id);

    int insert(Book book);

    int updateById(Book book);

    int deleteById(Long id);

    /**
     * 更新图书状态（available / borrowed）
     */
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    List<Book> selectByKeywordWithPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    long countByKeyword(@Param("keyword") String keyword);
}
