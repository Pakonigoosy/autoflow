package com.kargin.autoflow.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Сущность двигателя автомобиля
 */
@Entity
@Table(name = "engine")
@NamedQueries({
    @NamedQuery(name = "Engine.findAll", query = "SELECT e FROM Engine e"),
    @NamedQuery(name = "Engine.findBySerialNumber", query = "SELECT e FROM Engine e WHERE e.serialNumber = :serialNumber"),
    @NamedQuery(name = "Engine.findAvailable", query = "SELECT e FROM Engine e WHERE e.car IS NULL")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Engine implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Тип двигателя не может быть пустым")
    @Size(max = 50, message = "Тип двигателя не может быть длиннее 50 символов")
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    
    @NotNull(message = "Объем двигателя не может быть пустым")
    @DecimalMin(value = "0.1", message = "Объем двигателя должен быть больше 0")
    @Column(name = "volume", nullable = false, precision = 5, scale = 2)
    private BigDecimal volume;
    
    @NotNull(message = "Мощность двигателя не может быть пустой")
    @DecimalMin(value = "0.1", message = "Мощность двигателя должна быть больше 0")
    @Column(name = "power_kw", nullable = false, precision = 6, scale = 2)
    private BigDecimal powerKw;
    
    @NotBlank(message = "Серийный номер двигателя не может быть пустым")
    @Size(max = 50, message = "Серийный номер не может быть длиннее 50 символов")
    @Column(name = "serial_number", nullable = false, length = 50, unique = true)
    private String serialNumber;
    
    @OneToOne(mappedBy = "engine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @XmlTransient
    private Car car;
    
    public Engine() {
    }
    
    public Engine(String type, BigDecimal volume, BigDecimal powerKw, String serialNumber) {
        this.type = type;
        this.volume = volume;
        this.powerKw = powerKw;
        this.serialNumber = serialNumber;
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
    
    public BigDecimal getVolume() {
        return volume;
    }
    
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    
    public BigDecimal getPowerKw() {
        return powerKw;
    }
    
    public void setPowerKw(BigDecimal powerKw) {
        this.powerKw = powerKw;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isCarLinked() {
        return car != null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Engine engine = (Engine) o;
        
        return Objects.equals(id, engine.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Engine{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", volume=" + volume +
                ", powerKw=" + powerKw +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
