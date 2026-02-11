package com.kargin.autoflow.service.specification;

import com.kargin.autoflow.entity.Car;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CarSpecification extends BaseQuerySpecification<Car> {
    public CarSpecification(String search, String sortBy, String sortOrder) {
        super(search, sortBy, sortOrder);
    }

    protected Predicate buildSearchPredicate(Root<Car> root, CriteriaBuilder cb, String search) {
        String lowerSearch = search.toLowerCase();

        List<Predicate> predicates = Arrays.asList(
                cb.like(cb.lower(root.get("body").get("type")), "%" + lowerSearch + "%"),
                cb.like(cb.lower(root.get("body").get("color")), "%" + lowerSearch + "%"),
                cb.like(cb.lower(root.get("body").get("vin")), "%" + lowerSearch + "%"),
                cb.like(cb.lower(root.get("engine").get("type")), "%" + lowerSearch + "%"),
                cb.like(cb.lower(root.get("engine").get("serialNumber")), "%" + lowerSearch + "%"),
                cb.like(cb.lower(root.get("transmission").get("type")), "%" + lowerSearch + "%"),
                cb.like(cb.lower(root.get("transmission").get("serialNumber")), "%" + lowerSearch + "%")
        );

        return cb.or(predicates.toArray(new Predicate[0]));
    }

    protected Path<?> getSortPath(Root<Car> root, String sortBy) {
        Map<String, Function<Root<Car>, Path<?>>> pathMappings = Map.of(
                "body.type", r -> r.get("body").get("type"),
                "body.color", r -> r.get("body").get("color"),
                "body.vin", r -> r.get("body").get("vin"),
                "engine.type", r -> r.get("engine").get("type"),
                "engine.serialNumber", r -> r.get("engine").get("serialNumber"),
                "transmission.type", r -> r.get("transmission").get("type"),
                "transmission.serialNumber", r -> r.get("transmission").get("serialNumber")
        );

        return pathMappings.getOrDefault(sortBy, r -> r.get(sortBy)).apply(root);
    }

    public Class<Car> getEntityClass() {
        return Car.class;
    }

    @Override
    protected List<String> getFetchFields() {
        return List.of("body", "engine", "transmission");
    }
}
