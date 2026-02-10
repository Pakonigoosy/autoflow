package com.kargin.autoflow.service;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Engine;
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
public class EngineService {

    @PersistenceContext(unitName = "autoflowPU")
    private EntityManager em;

    @EJB
    private PaginationService paginationService;

    /**
     * Создает новый двигатель
     * @param engine двигатель для создания
     * @return созданный двигатель
     */
    public Engine create(Engine engine) {
        em.persist(engine);
        em.flush();
        return engine;
    }

    /**
     * Обновляет существующий двигатель
     * @param engine двигатель для обновления
     * @return обновленный двигатель
     * @throws ComponentNotFoundException если двигатель не найден
     * @throws ComponentInUseException если двигатель используется в автомобиле
     */
    public Engine update(Engine engine) throws ComponentNotFoundException, ComponentInUseException {
        Engine existing = em.find(Engine.class, engine.getId());
        if (existing == null) {
            throw new ComponentNotFoundException("Двигатель с ID " + engine.getId() + " не найден");
        }

        if (existing.hasCar()) {
            throw new ComponentInUseException("Двигатель уже используется в автомобиле и не может быть изменен");
        }
        
        return em.merge(engine);
    }

    /**
     * Удаляет двигатель
     * @param id ID двигателя
     * @throws ComponentNotFoundException если двигатель не найден
     * @throws ComponentInUseException если двигатель используется в автомобиле
     */
    public void delete(Long id) throws ComponentNotFoundException, ComponentInUseException {
        Engine engine = em.find(Engine.class, id);
        if (engine == null) {
            throw new ComponentNotFoundException("Двигатель с ID " + id + " не найден");
        }

        if (engine.hasCar()) {
            throw new ComponentInUseException("Двигатель используется в автомобиле и не может быть удален. Сначала разберите автомобиль.");
        }
        
        em.remove(engine);
    }

    public Engine findById(Long id) {
        return em.find(Engine.class, id);
    }

    public Engine findBySerialNumber(String serialNumber) {
        TypedQuery<Engine> query = em.createNamedQuery("Engine.findBySerialNumber", Engine.class);
        query.setParameter("serialNumber", serialNumber);
        List<Engine> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Engine> findAll() {
        return em.createNamedQuery("Engine.findAll", Engine.class).getResultList();
    }

    public List<Engine> findAvailable() {
        return em.createNamedQuery("Engine.findAvailable", Engine.class).getResultList();
    }

    public PaginationHelper<Engine> findAll(PaginationParams params) {

        QuerySpecification<Engine> specification = new TypeSerialSpecification<>(
                Engine.class,
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
