package com.kargin.autoflow.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

/**
 * DTO автомобиля для REST API. Содержит только ID компонентов,
 * без вложенных объектов — обход циклической сериализации.
 */
@XmlRootElement
public class CarDto {

    private Long id;
    private Long bodyId;
    private Long engineId;
    private Long transmissionId;
    private Date assembledDate;
    /** VIN из кузова (удобство для клиента). */
    private String vin;

    public CarDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBodyId() {
        return bodyId;
    }

    public void setBodyId(Long bodyId) {
        this.bodyId = bodyId;
    }

    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public Long getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Long transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Date getAssembledDate() {
        return assembledDate;
    }

    public void setAssembledDate(Date assembledDate) {
        this.assembledDate = assembledDate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto that = (CarDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
