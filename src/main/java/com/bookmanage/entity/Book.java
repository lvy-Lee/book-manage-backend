package com.bookmanage.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 图书实体，对应数据库 book 表
 */
@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String category;
    private String description;
    private String status;
    private LocalDateTime createTime;
}
