package com.kargin.autoflow.service.specification;

import javax.persistence.criteria.*;

public abstract class BaseQuerySpecification<T> implements QuerySpecification<T> {
    protected final String search;
    protected final String sortBy;
    protected final String sortOrder;

    protected BaseQuerySpecification(String search, String sortBy, String sortOrder) {
        this.search = search;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    protected abstract Predicate buildSearchPredicate(Root<T> root, CriteriaBuilder cb, String search);

    protected abstract Path<?> getSortPath(Root<T> root, String sortBy);

    public CriteriaQuery<T> buildQuery(CriteriaBuilder cb, Root<T> root) {
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        root = query.from(getEntityClass());

        query.select(root);

        if (search != null && !search.trim().isEmpty()) {
            query.where(buildSearchPredicate(root, cb, search));
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            Path<?> sortPath = getSortPath(root, sortBy);
            query.orderBy(sortOrder.equalsIgnoreCase("desc") ?
                    cb.desc(sortPath) : cb.asc(sortPath));
        }

        return query;
    }

    public CriteriaQuery<Long> buildCountQuery(CriteriaBuilder cb, Root<T> root) {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        root = query.from(getEntityClass());

        query.select(cb.count(root));

        if (search != null && !search.trim().isEmpty()) {
            query.where(buildSearchPredicate(root, cb, search));
        }

        return query;
    }
}
