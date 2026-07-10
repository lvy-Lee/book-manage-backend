package com.bookmanage.controller;

import com.bookmanage.common.PageData;
import com.bookmanage.common.Result;
import com.bookmanage.dto.BookRequest;
import com.bookmanage.dto.BookResponse;
import com.bookmanage.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 图书管理接口，基础路径 /api/books
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 查询图书列表，支持按标题/作者模糊搜索，支持分页
     */
    @GetMapping
    public Result<PageData<BookResponse>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(bookService.list(keyword, page, size));
    }

    /**
     * 根据 ID 查询图书详情
     */
    @GetMapping("/{id}")
    public Result<BookResponse> getById(@PathVariable Long id) {
        BookResponse r = bookService.getById(id);
        if (r == null) {
            return Result.error(404, "图书不存在");
        }
        return Result.success(r);
    }

    /**
     * 新增图书（仅管理员可用）
     */
    @PostMapping
    public Result<BookResponse> create(@Valid @RequestBody BookRequest request,
                                        HttpServletRequest httpRequest) {
        String role = (String) httpRequest.getAttribute("currentUserRole");
        if (!"admin".equals(role)) {
            return Result.error(403, "仅管理员可操作");
        }
        BookResponse r = bookService.create(request);
        return Result.success(r);
    }

    /**
     * 修改图书（仅管理员可用）
     */
    @PutMapping("/{id}")
    public Result<BookResponse> update(@PathVariable Long id,
                                        @Valid @RequestBody BookRequest request,
                                        HttpServletRequest httpRequest) {
        String role = (String) httpRequest.getAttribute("currentUserRole");
        if (!"admin".equals(role)) {
            return Result.error(403, "仅管理员可操作");
        }
        BookResponse r = bookService.update(id, request);
        if (r == null) {
            return Result.error(404, "图书不存在");
        }
        return Result.success(r);
    }

    /**
     * 删除图书（仅管理员可用）
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        String role = (String) httpRequest.getAttribute("currentUserRole");
        if (!"admin".equals(role)) {
            return Result.error(403, "仅管理员可操作");
        }
        bookService.delete(id);
        return Result.success();
    }
}
