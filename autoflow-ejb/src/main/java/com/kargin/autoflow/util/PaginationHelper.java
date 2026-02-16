package com.kargin.autoflow.util;

import java.util.List;

/**
 * Служебный класс сервисного слоя для пагинированных результатов.
 * Не предназначен для прямой сериализации в REST.
 * Для REST используйте DTO {@code com.kargin.autoflow.rest.dto.PaginatedResponse}.
 */
public class PaginationHelper<T> {
    private final List<T> items;
    private final int currentPage;
    private final int pageSize;
    private final long totalItems;
    private final int totalPages;

    public PaginationHelper(List<T> items, int currentPage, int pageSize, long totalItems) {
        this.items = items;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }

    public List<T> getItems() {
        return items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isNextPage() {
        return currentPage < totalPages;
    }

    public boolean isPreviousPage() {
        return currentPage > 1;
    }
}
