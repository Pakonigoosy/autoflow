package com.kargin.autoflow.service.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TypeSerialSpecification<T> extends BaseQuerySpecification<T> {

    private final Class<T> entityClass;

    public TypeSerialSpecification(Class<T> entityClass, String search, String sortBy, String sortOrder) {
        super(search, sortBy, sortOrder);
        this.entityClass = entityClass;
    }

    protected Predicate buildSearchPredicate(Root<T> root, CriteriaBuilder cb, String search) {
        String lowerSearch = search.toLowerCase();

        Predicate typePredicate = cb.like(cb.lower(root.get("type")), "%" + lowerSearch + "%");
        Predicate serialPredicate = cb.like(cb.lower(root.get("serialNumber")), "%" + lowerSearch + "%");

        return cb.or(typePredicate, serialPredicate);
    }

    protected Path<?> getSortPath(Root<T> root, String sortBy) {
        return root.get(sortBy);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
