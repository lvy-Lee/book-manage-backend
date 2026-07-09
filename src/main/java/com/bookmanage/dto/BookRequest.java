package com.bookmanage.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 新增/修改图书的请求体
 */
@Data
public class BookRequest {
    @NotBlank(message = "书名不能为空")
    private String title;

    private String author;
    private String publisher;
    private String isbn;
    private String category;
    private String description;
}
