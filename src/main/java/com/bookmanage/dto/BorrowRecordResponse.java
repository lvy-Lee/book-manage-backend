package com.bookmanage.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 借阅记录响应体（包含书名和用户名）
 */
@Data
public class BorrowRecordResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookTitle;
    private String borrowTime;
    private String returnTime;
    private String status;

    public static BorrowRecordResponse of(Long id, Long userId, String username,
                                           Long bookId, String bookTitle,
                                           LocalDateTime borrowTime, LocalDateTime returnTime,
                                           String status) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        BorrowRecordResponse r = new BorrowRecordResponse();
        r.setId(id);
        r.setUserId(userId);
        r.setUsername(username);
        r.setBookId(bookId);
        r.setBookTitle(bookTitle);
        r.setBorrowTime(borrowTime != null ? borrowTime.format(fmt) : null);
        r.setReturnTime(returnTime != null ? returnTime.format(fmt) : null);
        r.setStatus(status);
        return r;
    }
}
