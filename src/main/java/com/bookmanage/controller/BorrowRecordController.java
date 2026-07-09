package com.bookmanage.controller;

import com.bookmanage.common.Result;
import com.bookmanage.dto.BorrowRecordResponse;
import com.bookmanage.dto.BorrowRequest;
import com.bookmanage.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 借阅记录接口，基础路径 /api/borrow
 */
@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    /**
     * 借书
     */
    @PostMapping
    public Result<BorrowRecordResponse> borrow(@Valid @RequestBody BorrowRequest request,
                                                HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        try {
            BorrowRecordResponse r = borrowRecordService.borrow(userId, request.getBookId());
            return Result.success(r);
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        }
    }

    /**
     * 还书
     */
    @PutMapping("/{id}/return")
    public Result<BorrowRecordResponse> returnBook(@PathVariable Long id,
                                                    HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        try {
            BorrowRecordResponse r = borrowRecordService.returnBook(id, userId);
            return Result.success(r);
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        }
    }

    /**
     * 查询当前用户的借阅记录
     */
    @GetMapping("/my")
    public Result<List<BorrowRecordResponse>> myRecords(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        return Result.success(borrowRecordService.getMyRecords(userId));
    }

    /**
     * 管理员查询全部借阅记录
     */
    @GetMapping("/all")
    public Result<List<BorrowRecordResponse>> allRecords(HttpServletRequest httpRequest) {
        String role = (String) httpRequest.getAttribute("currentUserRole");
        if (!"admin".equals(role)) {
            return Result.error(403, "仅管理员可查看");
        }
        return Result.success(borrowRecordService.getAllRecords());
    }
}
