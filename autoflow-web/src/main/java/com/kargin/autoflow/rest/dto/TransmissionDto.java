package com.kargin.autoflow.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * DTO трансмиссии для REST API. Не содержит ссылки на Car — только carId.
 */
@XmlRootElement
public class TransmissionDto {

    private Long id;
    private String type;
    private String serialNumber;
    /** ID автомобиля, если трансмиссия установлена; иначе null. */
    private Long carId;

    public TransmissionDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransmissionDto that = (TransmissionDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
