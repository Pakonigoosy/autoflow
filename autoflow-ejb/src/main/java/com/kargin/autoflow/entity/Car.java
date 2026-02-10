package com.kargin.autoflow.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Сущность автомобиля
 */
@Entity
@Table(name = "car")
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
    @NamedQuery(name = "Car.findByVin", query = "SELECT c FROM Car c WHERE c.vin = :vin")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "VIN автомобиля не может быть пустым")
    @Size(max = 17, message = "VIN не может быть длиннее 17 символов")
    @Column(name = "vin", nullable = false, length = 17, unique = true)
    private String vin;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "body_id", unique = true)
    private CarBody body;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "engine_id", unique = true)
    private Engine engine;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "transmission_id", unique = true)
    private Transmission transmission;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assembled_date")
    private Date assembledDate;
    
    public Car() {
    }
    
    public Car(String vin, CarBody body, Engine engine, Transmission transmission, Date assembledDate) {
        this.vin = vin;
        this.body = body;
        this.engine = engine;
        this.transmission = transmission;
        this.assembledDate = assembledDate;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public CarBody getBody() {
        return body;
    }
    
    public void setBody(CarBody body) {
        if (this.body != null) {
            this.body.setCar(null);
        }
        this.body = body;
        if (body != null) {
            body.setCar(this);
        }
    }
    
    public Engine getEngine() {
        return engine;
    }
    
    public void setEngine(Engine engine) {
        if (this.engine != null) {
            this.engine.setCar(null);
        }
        this.engine = engine;
        if (engine != null) {
            engine.setCar(this);
        }
    }
    
    public Transmission getTransmission() {
        return transmission;
    }
    
    public void setTransmission(Transmission transmission) {
        if (this.transmission != null) {
            this.transmission.setCar(null);
        }
        this.transmission = transmission;
        if (transmission != null) {
            transmission.setCar(this);
        }
    }
    
    public Date getAssembledDate() {
        return assembledDate;
    }
    
    public void setAssembledDate(Date assembledDate) {
        this.assembledDate = assembledDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Car car = (Car) o;
        
        return Objects.equals(id, car.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", assembledDate=" + assembledDate +
                '}';
    }
}
