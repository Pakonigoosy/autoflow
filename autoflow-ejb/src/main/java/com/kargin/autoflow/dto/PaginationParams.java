package com.kargin.autoflow.dto;


/**
 * Immutable DTO для параметров пагинации, поиска и сортировки.
 * Извлекает и нормализует параметры из HTTP-запроса.
 */
public record PaginationParams(int page, int pageSize, String search, String sortBy, String sortOrder) {

}