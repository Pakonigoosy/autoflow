package com.kargin.autoflow.service;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.service.specification.QuerySpecification;
import com.kargin.autoflow.service.specification.TypeSerialSpecification;
import com.kargin.autoflow.util.PaginationHelper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TransmissionService {

    @PersistenceContext(unitName = "autoflowPU")
    private EntityManager em;

    @EJB
    private PaginationService paginationService;

    /**
     * Создает новую трансмиссию
     * @param transmission трансмиссия для создания
     * @return созданная трансмиссия
     */
    public Transmission create(Transmission transmission) {
        em.persist(transmission);
        em.flush();
        return transmission;
    }

    /**
     * Обновляет существующую трансмиссию
     * @param transmission трансмиссия для обновления
     * @return обновленная трансмиссия
     * @throws ComponentNotFoundException если трансмиссия не найдена
     * @throws ComponentInUseException если трансмиссия используется в автомобиле
     */
    public Transmission update(Transmission transmission) throws ComponentNotFoundException, ComponentInUseException {
        Transmission existing = em.find(Transmission.class, transmission.getId());
        if (existing == null) {
            throw new ComponentNotFoundException("Трансмиссия с ID " + transmission.getId() + " не найдена");
        }

        if (existing.hasCar()) {
            throw new ComponentInUseException("Трансмиссия уже используется в автомобиле и не может быть изменена");
        }
        
        return em.merge(transmission);
    }

    /**
     * Удаляет трансмиссию
     * @param id ID трансмиссии
     * @throws ComponentNotFoundException если трансмиссия не найдена
     * @throws ComponentInUseException если трансмиссия используется в автомобиле
     */
    public void delete(Long id) throws ComponentNotFoundException, ComponentInUseException {
        Transmission transmission = em.find(Transmission.class, id);
        if (transmission == null) {
            throw new ComponentNotFoundException("Трансмиссия с ID " + id + " не найдена");
        }

        if (transmission.hasCar()) {
            throw new ComponentInUseException("Трансмиссия используется в автомобиле и не может быть удалена. Сначала разберите автомобиль.");
        }
        
        em.remove(transmission);
    }

    public Transmission findById(Long id) {
        return em.find(Transmission.class, id);
    }

    public Transmission findBySerialNumber(String serialNumber) {
        TypedQuery<Transmission> query = em.createNamedQuery("Transmission.findBySerialNumber", Transmission.class);
        query.setParameter("serialNumber", serialNumber);
        List<Transmission> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Transmission> findAll() {
        return em.createNamedQuery("Transmission.findAll", Transmission.class).getResultList();
    }

    public List<Transmission> findAvailable() {
        return em.createNamedQuery("Transmission.findAvailable", Transmission.class).getResultList();
    }

    public PaginationHelper<Transmission> findAll(PaginationParams params) {

        QuerySpecification<Transmission> specification = new TypeSerialSpecification<>(
                Transmission.class,
                params.search(),
                params.sortBy(),
                params.sortOrder()
        );

        return paginationService.paginate(
                specification,
                params.page(),
                params.pageSize()
        );
    }
}
