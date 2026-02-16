package com.kargin.autoflow.rest.dto;

import com.kargin.autoflow.util.PaginationHelper;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DTO пагинированного ответа для REST API.
 * Совместим с JAXB и Jackson: конструктор по умолчанию и сеттеры.
 * Создаётся из результата сервисного слоя {@link PaginationHelper}.
 * Для ответов используйте DTO, а не сущности — во избежание циклической сериализации.
 */
@XmlRootElement(name = "paginatedResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({CarBodyDto.class, CarDto.class, EngineDto.class, TransmissionDto.class})
public class PaginatedResponse<T> {

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<T> items = new ArrayList<>();

    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean nextPage;
    private boolean previousPage;

    public PaginatedResponse() {
    }

    /**
     * Создаёт пагинированный ответ с преобразованием элементов сущностей в DTO.
     * Используйте для REST, чтобы избежать циклической сериализации (например Entity → DTO).
     */
    public static <E, D> PaginatedResponse<D> from(PaginationHelper<E> helper, Function<E, D> mapper) {
        PaginatedResponse<D> dto = new PaginatedResponse<>();
        dto.setItems(helper.getItems().stream().map(mapper).collect(Collectors.toList()));
        dto.setCurrentPage(helper.getCurrentPage());
        dto.setPageSize(helper.getPageSize());
        dto.setTotalItems(helper.getTotalItems());
        dto.setTotalPages(helper.getTotalPages());
        dto.setNextPage(helper.isNextPage());
        dto.setPreviousPage(helper.isPreviousPage());
        return dto;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(boolean previousPage) {
        this.previousPage = previousPage;
    }
}
