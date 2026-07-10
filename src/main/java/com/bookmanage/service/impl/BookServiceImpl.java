package com.bookmanage.service.impl;

import com.bookmanage.common.PageData;
import com.bookmanage.dto.BookRequest;
import com.bookmanage.dto.BookResponse;
import com.bookmanage.entity.Book;
import com.bookmanage.mapper.BookMapper;
import com.bookmanage.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图书业务实现
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    @Override
    public PageData<BookResponse> list(String keyword, int page, int size) {
        String kw = (keyword != null) ? keyword.trim() : "";
        int offset = (page - 1) * size;
        long total = bookMapper.countByKeyword(kw);
        List<Book> books = bookMapper.selectByKeywordWithPage(kw, offset, size);
        List<BookResponse> list = books.stream().map(this::toResponse).collect(Collectors.toList());
        return new PageData<>(total, page, size, list);
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = bookMapper.selectById(id);
        return book != null ? toResponse(book) : null;
    }

    @Override
    public BookResponse create(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setStatus("available");
        book.setCreateTime(LocalDateTime.now());
        bookMapper.insert(book);
        return toResponse(book);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = bookMapper.selectById(id);
        if (book == null) {
            return null;
        }
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        bookMapper.updateById(book);
        return toResponse(book);
    }

    @Override
    public void delete(Long id) {
        bookMapper.deleteById(id);
    }

    private BookResponse toResponse(Book book) {
        return BookResponse.of(
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getPublisher(), book.getIsbn(), book.getCategory(),
                book.getDescription(), book.getStatus(), book.getCreateTime());
    }
}
