package com.kargin.autoflow.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Сущность кузова автомобиля
 */
@Entity
@Table(name = "car_body")
@NamedQueries({
    @NamedQuery(name = "CarBody.findAll", query = "SELECT cb FROM CarBody cb"),
    @NamedQuery(name = "CarBody.findByVin", query = "SELECT cb FROM CarBody cb WHERE cb.vin = :vin"),
    @NamedQuery(name = "CarBody.findAvailable", query = "SELECT cb FROM CarBody cb WHERE cb.car IS NULL")
})
public class CarBody implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Тип кузова не может быть пустым")
    @Size(max = 50, message = "Тип кузова не может быть длиннее 50 символов")
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    
    @NotBlank(message = "Цвет кузова не может быть пустым")
    @Size(max = 30, message = "Цвет кузова не может быть длиннее 30 символов")
    @Column(name = "color", nullable = false, length = 30)
    private String color;
    
    @NotNull(message = "Количество дверей не может быть пустым")
    @Min(value = 2, message = "Количество дверей должно быть не менее 2")
    @Column(name = "door_count", nullable = false)
    private Integer doorCount;
    
    @Size(max = 17, message = "VIN не может быть длиннее 17 символов")
    @Column(name = "vin", length = 17, unique = true)
    private String vin;
    
    @OneToOne(mappedBy = "body", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Car car;
    
    public CarBody() {
    }
    
    public CarBody(String type, String color, Integer doorCount, String vin) {
        this.type = type;
        this.color = color;
        this.doorCount = doorCount;
        this.vin = vin;
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
    
    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }

    public boolean hasCar() {
        return car != null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        CarBody carBody = (CarBody) o;
        
        return Objects.equals(id, carBody.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "CarBody{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", doorCount=" + doorCount +
                ", vin='" + vin + '\'' +
                '}';
    }
}
