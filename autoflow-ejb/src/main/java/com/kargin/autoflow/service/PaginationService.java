package com.kargin.autoflow.service;

import com.kargin.autoflow.service.specification.QuerySpecification;
import com.kargin.autoflow.util.PaginationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class PaginationService {

    @PersistenceContext(unitName = "autoflowPU")
    private EntityManager em;

    public <T> PaginationHelper<T> paginate(QuerySpecification<T> specification,
                                            int page, int pageSize) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        Root<T> root = cb.createQuery(specification.getEntityClass()).from(specification.getEntityClass());
        CriteriaQuery<T> criteriaQuery = specification.buildQuery(cb, root);
        TypedQuery<T> query = em.createQuery(criteriaQuery);

        CriteriaQuery<Long> countQuery = specification.buildCountQuery(cb, root);
        long totalItems = em.createQuery(countQuery).getSingleResult();

        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<T> items = query.getResultList();
        return new PaginationHelper<>(items, page, pageSize, totalItems);
    }
}
