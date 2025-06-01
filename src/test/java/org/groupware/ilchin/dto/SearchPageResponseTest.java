package org.groupware.ilchin.dto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SearchPageResponseTest {

    @Test
    void 정상_데이터_생성_테스트() {
        // Given
        Long totalElements = 100L;
        Long totalPages = 10L;
        Integer currentPage = 1;
        Integer pageSize = 10;
        List<String> content = Arrays.asList("Item1", "Item2", "Item3");

        // When
        SearchPageResponse<String> response = SearchPageResponse.of(
                totalElements,
                totalPages,
                currentPage,
                pageSize,
                content
        );

        // Then
        assertNotNull(response);
        assertEquals(totalElements, response.totalElements());
        assertEquals(totalPages, response.totalPages());
        assertEquals(currentPage, response.currentPage());
        assertEquals(pageSize, response.pageSize());
        assertEquals(content, response.content());
        assertEquals(3, response.content().size());
    }

    @Test
    void 빈_콘텐츠_생성_테스트() {
        // Given
        Long totalElements = 0L;
        Long totalPages = 0L;
        Integer currentPage = 1;
        Integer pageSize = 10;
        List<String> content = List.of();

        // When
        SearchPageResponse<String> response = SearchPageResponse.of(
                totalElements,
                totalPages,
                currentPage,
                pageSize,
                content
        );

        // Then
        assertNotNull(response);
        assertEquals(totalElements, response.totalElements());
        assertEquals(totalPages, response.totalPages());
        assertEquals(currentPage, response.currentPage());
        assertEquals(pageSize, response.pageSize());
        assertTrue(response.content().isEmpty());
    }

    @Test
    void Null_생성_테스트() {
        // When
        SearchPageResponse<String> response = SearchPageResponse.of(
                null,
                null,
                null,
                null,
                null
        );

        // Then
        assertNotNull(response);
        assertNull(response.totalElements());
        assertNull(response.totalPages());
        assertNull(response.currentPage());
        assertNull(response.pageSize());
        assertNull(response.content());
    }
}