package com.bookmanage.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 借阅记录实体，对应数据库 borrow_record 表
 */
@Data
public class BorrowRecord {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
    private String status;
}
