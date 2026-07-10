package com.bookmanage.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageData<T> {
    private long total;
    private int page;
    private int size;
    private List<T> list;
}
