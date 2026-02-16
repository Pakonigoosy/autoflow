package com.kargin.autoflow.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * DTO кузова для REST API. Не содержит ссылки на Car — только carId для обхода циклической сериализации.
 */
@XmlRootElement
public class CarBodyDto {

    private Long id;
    private String type;
    private String color;
    private Integer doorCount;
    private String vin;
    /** ID автомобиля, если кузов используется в машине; иначе null. */
    private Long carId;

    public CarBodyDto() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(Integer doorCount) {
        this.doorCount = doorCount;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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
        CarBodyDto that = (CarBodyDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
