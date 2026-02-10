package com.kargin.autoflow.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Сущность трансмиссии автомобиля
 */
@Entity
@Table(name = "transmission")
@NamedQueries({
    @NamedQuery(name = "Transmission.findAll", query = "SELECT t FROM Transmission t"),
    @NamedQuery(name = "Transmission.findBySerialNumber", query = "SELECT t FROM Transmission t WHERE t.serialNumber = :serialNumber"),
    @NamedQuery(name = "Transmission.findAvailable", query = "SELECT t FROM Transmission t WHERE t.car IS NULL")
})
public class Transmission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Тип трансмиссии не может быть пустым")
    @Size(max = 50, message = "Тип трансмиссии не может быть длиннее 50 символов")
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    
    @NotBlank(message = "Серийный номер трансмиссии не может быть пустым")
    @Size(max = 50, message = "Серийный номер не может быть длиннее 50 символов")
    @Column(name = "serial_number", nullable = false, length = 50, unique = true)
    private String serialNumber;
    
    @OneToOne(mappedBy = "transmission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Car car;
    
    public Transmission() {
    }
    
    public Transmission(String type, String serialNumber) {
        this.type = type;
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

    public boolean hasCar() {
        return car != null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Transmission that = (Transmission) o;
        
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Transmission{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
