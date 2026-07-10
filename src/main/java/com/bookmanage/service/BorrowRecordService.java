package com.bookmanage.service;

import com.bookmanage.dto.BorrowRecordResponse;

import java.util.List;

/**
 * 借阅记录业务接口
 */
public interface BorrowRecordService {
    /**
     * 借书
     * @param userId 当前用户 ID
     * @param bookId 图书 ID
     * @return 借阅记录
     * @throws IllegalArgumentException 图书不存在或已被借出
     */
    BorrowRecordResponse borrow(Long userId, Long bookId);

    /**
     * 还书
     * @param recordId 借阅记录 ID
     * @param userId 当前用户 ID
     * @param isAdmin 是否为管理员（管理员可代还）
     * @return 借阅记录
     * @throws IllegalArgumentException 记录不存在、不属于当前用户、或已归还
     */
    BorrowRecordResponse returnBook(Long recordId, Long userId, boolean isAdmin);

    /**
     * 查询当前用户的借阅记录
     */
    List<BorrowRecordResponse> getMyRecords(Long userId);

    /**
     * 管理员查询全部借阅记录
     */
    List<BorrowRecordResponse> getAllRecords();
}
