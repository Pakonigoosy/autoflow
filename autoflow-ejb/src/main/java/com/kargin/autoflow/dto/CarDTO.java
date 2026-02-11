package com.kargin.autoflow.dto;

import com.kargin.autoflow.entity.Car;

import java.math.BigDecimal;
import java.util.Date;

public class CarDTO {
    private final Long id;
    private final String bodyType;
    private final String bodyColor;
    private final String bodyVin;
    private final String engineType;
    private final BigDecimal enginePowerKw;
    private final String engineSerialNumber;
    private final String transmissionType;
    private final String transmissionSerialNumber;
    private final Date assembledDate;

    public  CarDTO(Car car) {
        this.id = car.getId();
        var body = car.getBody();
        this.bodyType = body.getType();
        this.bodyColor = body.getColor();
        this.bodyVin = body.getVin();
        var engine = car.getEngine();
        this.engineType = engine.getType();
        this.enginePowerKw = engine.getPowerKw();
        this.engineSerialNumber = engine.getSerialNumber();
        var transmission = car.getTransmission();
        this.transmissionType = transmission.getType();
        this.transmissionSerialNumber = transmission.getSerialNumber();
        this.assembledDate = car.getAssembledDate();
    }

    // Геттеры для JSP
    public Long getId() { return id; }
    public String getBodyType() { return bodyType; }
    public String getBodyColor() { return bodyColor; }
    public String getBodyVin() { return bodyVin; }
    public String getEngineType() { return engineType; }
    public BigDecimal getEnginePowerKw() { return enginePowerKw; }
    public String getEngineSerialNumber() { return engineSerialNumber; }
    public String getTransmissionType() { return transmissionType; }
    public String getTransmissionSerialNumber() { return transmissionSerialNumber; }
    public Date getAssembledDate() { return assembledDate; }
}
