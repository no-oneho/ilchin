package org.groupware.ilchin.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchPageResponse<T>(
        Long totalElements,
        Long totalPages,
        Integer currentPage,
        Integer pageSize,
        List<T> content) {

    public static <T> SearchPageResponse<T> of(
            Long totalElements,
            Long totalPages,
            Integer currentPage,
            Integer pageSize,
            List<T> content
    ) {
        return SearchPageResponse.<T>builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .content(content)
                .build();
    }
}