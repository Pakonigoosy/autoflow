package com.kargin.autoflow.rest.dto;

import com.kargin.autoflow.entity.Car;
import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.entity.Engine;
import com.kargin.autoflow.entity.Transmission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Преобразование сущностей в DTO для REST. Используется только на границе REST-слоя.
 * Сущности в сервисах и сервлетах не меняются.
 */
public final class RestDtoMapper {

    private RestDtoMapper() {
    }

    public static CarBodyDto toDto(CarBody entity) {
        if (entity == null) return null;
        CarBodyDto dto = new CarBodyDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setColor(entity.getColor());
        dto.setDoorCount(entity.getDoorCount());
        dto.setVin(entity.getVin());
        dto.setCarId(entity.getCar() != null ? entity.getCar().getId() : null);
        return dto;
    }

    public static List<CarBodyDto> toCarBodyDtoList(List<CarBody> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(RestDtoMapper::toDto).collect(Collectors.toList());
    }

    public static EngineDto toDto(Engine entity) {
        if (entity == null) return null;
        EngineDto dto = new EngineDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setVolume(entity.getVolume());
        dto.setPowerKw(entity.getPowerKw());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setCarId(entity.getCar() != null ? entity.getCar().getId() : null);
        return dto;
    }

    public static List<EngineDto> toEngineDtoList(List<Engine> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(RestDtoMapper::toDto).collect(Collectors.toList());
    }

    public static TransmissionDto toDto(Transmission entity) {
        if (entity == null) return null;
        TransmissionDto dto = new TransmissionDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setCarId(entity.getCar() != null ? entity.getCar().getId() : null);
        return dto;
    }

    public static List<TransmissionDto> toTransmissionDtoList(List<Transmission> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(RestDtoMapper::toDto).collect(Collectors.toList());
    }

    public static CarDto toDto(Car entity) {
        if (entity == null) return null;
        CarDto dto = new CarDto();
        dto.setId(entity.getId());
        dto.setBodyId(entity.getBody() != null ? entity.getBody().getId() : null);
        dto.setEngineId(entity.getEngine() != null ? entity.getEngine().getId() : null);
        dto.setTransmissionId(entity.getTransmission() != null ? entity.getTransmission().getId() : null);
        dto.setAssembledDate(entity.getAssembledDate());
        dto.setVin(entity.getVin());
        return dto;
    }

    public static CarBody toCarBody(CarBodyDto dto) {
        if (dto == null) return null;
        CarBody entity = new CarBody();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setColor(dto.getColor());
        entity.setDoorCount(dto.getDoorCount());
        entity.setVin(dto.getVin());
        return entity;
    }

    public static Engine toEngine(EngineDto dto) {
        if (dto == null) return null;
        Engine entity = new Engine();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setVolume(dto.getVolume());
        entity.setPowerKw(dto.getPowerKw());
        entity.setSerialNumber(dto.getSerialNumber());
        return entity;
    }

    public static Transmission toTransmission(TransmissionDto dto) {
        if (dto == null) return null;
        Transmission entity = new Transmission();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setSerialNumber(dto.getSerialNumber());
        return entity;
    }
}
