package com.kargin.autoflow.rest.dto;

import com.kargin.autoflow.entity.Car;
import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.entity.Engine;
import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.util.PaginationHelper;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO пагинированного ответа для REST API.
 * Совместим с JAXB и Jackson: конструктор по умолчанию и сеттеры.
 * Создаётся из результата сервисного слоя {@link PaginationHelper}.
 */
@XmlRootElement(name = "paginatedResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({CarBody.class, Car.class, Engine.class, Transmission.class})
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

    public static <T> PaginatedResponse<T> from(PaginationHelper<T> helper) {
        PaginatedResponse<T> dto = new PaginatedResponse<>();
        dto.setItems(helper.getItems());
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
