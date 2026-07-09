package com.bookmanage.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 图书查询的响应体
 */
@Data
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String category;
    private String description;
    private String status;
    private String createTime;

    public static BookResponse of(Long id, String title, String author, String publisher,
                                   String isbn, String category, String description,
                                   String status, LocalDateTime createTime) {
        BookResponse r = new BookResponse();
        r.setId(id);
        r.setTitle(title);
        r.setAuthor(author);
        r.setPublisher(publisher);
        r.setIsbn(isbn);
        r.setCategory(category);
        r.setDescription(description);
        r.setStatus(status);
        r.setCreateTime(createTime != null
                ? createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : null);
        return r;
    }
}
