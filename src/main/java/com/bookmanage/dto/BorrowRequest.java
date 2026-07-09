package com.bookmanage.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 借书请求体
 */
@Data
public class BorrowRequest {
    @NotNull(message = "图书ID不能为空")
    private Long bookId;
}
