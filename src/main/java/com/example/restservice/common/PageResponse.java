package com.example.restservice.common;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    long totalElements,
    int totalPages,
    int page,
    int size,
    boolean first,
    boolean last) {

  public static <T> PageResponse<T> from(Page<T> page) {

    boolean isFirst = page.page() == 0;
    boolean isLast = page.page() + 1 >= page.totalPages();

    return new PageResponse<>(
        page.content(),
        page.totalElements(),
        page.totalPages(),
        page.page(),
        page.size(),
        isFirst,
        isLast);
  }
}
