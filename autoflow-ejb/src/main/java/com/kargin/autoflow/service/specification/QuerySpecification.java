package com.kargin.autoflow.service.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface QuerySpecification<T> {
    CriteriaQuery<T> buildQuery(CriteriaBuilder cb, Root<T> root);
    CriteriaQuery<Long> buildCountQuery(CriteriaBuilder cb, Root<T> root);
    Class<T> getEntityClass();
}
