package com.bookmanage.service.impl;

import com.bookmanage.dto.BorrowRecordResponse;
import com.bookmanage.entity.Book;
import com.bookmanage.entity.BorrowRecord;
import com.bookmanage.mapper.BookMapper;
import com.bookmanage.mapper.BorrowRecordMapper;
import com.bookmanage.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 借阅记录业务实现
 */
@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private final BorrowRecordMapper borrowRecordMapper;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BorrowRecordResponse borrow(Long userId, Long bookId) {
        // 检查图书是否存在且可借
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("图书不存在");
        }
        if (!"available".equals(book.getStatus())) {
            throw new IllegalArgumentException("图书已被借出");
        }

        // 创建借阅记录
        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowTime(LocalDateTime.now());
        record.setStatus("borrowed");
        borrowRecordMapper.insert(record);

        // 更新图书状态为已借出
        bookMapper.updateStatus(bookId, "borrowed");

        return BorrowRecordResponse.of(
                record.getId(), userId, null, bookId,
                book.getTitle(), record.getBorrowTime(), null, "borrowed");
    }

    @Override
    @Transactional
    public BorrowRecordResponse returnBook(Long recordId, Long userId) {
        BorrowRecord record = borrowRecordMapper.selectById(recordId);
        if (record == null) {
            throw new IllegalArgumentException("借阅记录不存在");
        }
        if (!record.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权操作该记录");
        }
        if (!"borrowed".equals(record.getStatus())) {
            throw new IllegalArgumentException("该书已归还");
        }

        // 更新借阅记录
        record.setReturnTime(LocalDateTime.now());
        record.setStatus("returned");
        borrowRecordMapper.updateById(record);

        // 更新图书状态为可借
        bookMapper.updateStatus(record.getBookId(), "available");

        Book book = bookMapper.selectById(record.getBookId());
        return BorrowRecordResponse.of(
                record.getId(), userId, null, record.getBookId(),
                book.getTitle(), record.getBorrowTime(), record.getReturnTime(), "returned");
    }

    @Override
    public List<BorrowRecordResponse> getMyRecords(Long userId) {
        return borrowRecordMapper.selectByUserId(userId);
    }

    @Override
    public List<BorrowRecordResponse> getAllRecords() {
        return borrowRecordMapper.selectAllWithUser();
    }
}
