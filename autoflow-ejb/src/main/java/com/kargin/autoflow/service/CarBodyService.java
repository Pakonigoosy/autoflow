package com.kargin.autoflow.service;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.service.specification.CarBodySpecification;
import com.kargin.autoflow.service.specification.QuerySpecification;
import com.kargin.autoflow.util.PaginationHelper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CarBodyService {

    @PersistenceContext(unitName = "autoflowPU")
    private EntityManager em;

    @EJB
    PaginationService paginationService;

    /**
     * Создает новый кузов
     * @param carBody кузов для создания
     * @return созданный кузов
     */
    public CarBody create(CarBody carBody) {
        em.persist(carBody);
        em.flush();
        return carBody;
    }

    /**
     * Обновляет существующий кузов
     * @param carBody кузов для обновления
     * @return обновленный кузов
     * @throws ComponentNotFoundException если кузов не найден
     * @throws ComponentInUseException если кузов используется в автомобиле
     */
    public CarBody update(CarBody carBody) throws ComponentNotFoundException, ComponentInUseException {
        CarBody existing = em.find(CarBody.class, carBody.getId());
        if (existing == null) {
            throw new ComponentNotFoundException("Кузов с ID " + carBody.getId() + " не найден");
        }
        
        if (existing.isCarLinked()) {
            throw new ComponentInUseException("Кузов уже используется в автомобиле и не может быть изменен");
        }
        
        return em.merge(carBody);
    }

    /**
     * Удаляет кузов
     * @param id ID кузова
     * @throws ComponentNotFoundException если кузов не найден
     * @throws ComponentInUseException если кузов используется в автомобиле
     */
    public void delete(Long id) throws ComponentNotFoundException, ComponentInUseException {
        CarBody carBody = em.find(CarBody.class, id);
        if (carBody == null) {
            throw new ComponentNotFoundException("Кузов с ID " + id + " не найден");
        }
        
        if (carBody.isCarLinked()) {
            throw new ComponentInUseException("Кузов используется в автомобиле и не может быть удален. Сначала разберите автомобиль.");
        }
        
        em.remove(carBody);
    }

    public CarBody findById(Long id) {
        return em.find(CarBody.class, id);
    }

    public CarBody findByVin(String vin) {
        TypedQuery<CarBody> query = em.createNamedQuery("CarBody.findByVin", CarBody.class);
        query.setParameter("vin", vin);
        List<CarBody> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<CarBody> findAll() {
        return em.createNamedQuery("CarBody.findAll", CarBody.class).getResultList();
    }

    public List<CarBody> findAvailable() {
        return em.createNamedQuery("CarBody.findAvailable", CarBody.class).getResultList();
    }

    public PaginationHelper<CarBody> findAll(PaginationParams params) {
        QuerySpecification<CarBody> specification = new CarBodySpecification(
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
