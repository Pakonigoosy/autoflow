package com.kargin.autoflow.dto;


/**
 * Immutable DTO для параметров пагинации, поиска и сортировки.
 * Извлекает и нормализует параметры из HTTP-запроса.
 */
public record PaginationParams(int page, int pageSize, String search, String sortBy, String sortOrder) {

    public String getSearch() {
        return search;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}