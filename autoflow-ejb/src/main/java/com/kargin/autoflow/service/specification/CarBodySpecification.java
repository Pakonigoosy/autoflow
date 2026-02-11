package com.kargin.autoflow.service.specification;

import com.kargin.autoflow.entity.CarBody;

import javax.persistence.criteria.*;

public class CarBodySpecification extends BaseQuerySpecification<CarBody> {
    public CarBodySpecification(String search, String sortBy, String sortOrder) {
        super(search, sortBy, sortOrder);
    }

    protected Predicate buildSearchPredicate(Root<CarBody> root, CriteriaBuilder cb, String search) {
        String searchPattern = "%" + search.toLowerCase() + "%";

        Predicate typePredicate = cb.like(cb.lower(root.get("type")), searchPattern);
        Predicate colorPredicate = cb.like(cb.lower(root.get("color")), searchPattern);
        Predicate vinPredicate = cb.like(cb.lower(root.get("vin")), searchPattern);

        return cb.or(typePredicate, colorPredicate, vinPredicate);
    }

    protected Path<?> getSortPath(Root<CarBody> root, String sortBy) {
        return root.get(sortBy);
    }

    public Class<CarBody> getEntityClass() {
        return CarBody.class;
    }
}
