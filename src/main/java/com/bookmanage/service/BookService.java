package com.bookmanage.service;

import com.bookmanage.dto.BookRequest;
import com.bookmanage.dto.BookResponse;

import java.util.List;

/**
 * 图书业务接口
 */
public interface BookService {
    List<BookResponse> list(String keyword);

    BookResponse getById(Long id);

    BookResponse create(BookRequest request);

    BookResponse update(Long id, BookRequest request);

    void delete(Long id);
}
