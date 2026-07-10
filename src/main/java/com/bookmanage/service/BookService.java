package com.bookmanage.service;

import com.bookmanage.common.PageData;
import com.bookmanage.dto.BookRequest;
import com.bookmanage.dto.BookResponse;

/**
 * 图书业务接口
 */
public interface BookService {
    PageData<BookResponse> list(String keyword, int page, int size);

    BookResponse getById(Long id);

    BookResponse create(BookRequest request);

    BookResponse update(Long id, BookRequest request);

    void delete(Long id);
}
