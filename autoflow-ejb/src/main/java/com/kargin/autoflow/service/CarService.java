package com.kargin.autoflow.service;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Car;
import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.entity.Engine;
import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.exception.CarNotFoundException;
import com.kargin.autoflow.exception.ComponentInUseException;
import com.kargin.autoflow.exception.ComponentNotFoundException;
import com.kargin.autoflow.exception.DuplicateVinException;
import com.kargin.autoflow.service.specification.CarSpecification;
import com.kargin.autoflow.service.specification.QuerySpecification;
import com.kargin.autoflow.util.PaginationHelper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Stateless
public class CarService {

    @PersistenceContext(unitName = "autoflowPU")
    private EntityManager em;

    @EJB
    private PaginationService paginationService;

    @EJB
    private CarBodyService carBodyService;

    @EJB
    private EngineService engineService;

    @EJB
    private TransmissionService transmissionService;

    /**
     * Создает новый автомобиль
     * @param car автомобиль для создания
     * @return созданный автомобиль
     * @throws DuplicateVinException если VIN уже существует
     * @throws ComponentInUseException если компоненты уже используются
     */
    public Car create(Car car) throws DuplicateVinException, ComponentInUseException {
        if (car.getVin() != null) {
            checkUniqueVin(car);
        }

        if (car.getBody() != null && car.getBody().isCarLinked()) {
            throw new ComponentInUseException("Кузов уже используется в другом автомобиле");
        }
        if (car.getEngine() != null && car.getEngine().isCarLinked()) {
            throw new ComponentInUseException("Двигатель уже используется в другом автомобиле");
        }
        if (car.getTransmission() != null && car.getTransmission().isCarLinked()) {
            throw new ComponentInUseException("Трансмиссия уже используется в другом автомобиле");
        }
        
        if (car.getAssembledDate() == null) {
            car.setAssembledDate(new Date());
        }
        
        em.persist(car);
        em.flush();
        return car;
    }

    private void checkUniqueVin(Car car) throws DuplicateVinException {
        TypedQuery<Car> query = em.createNamedQuery("Car.findByVin", Car.class);
        query.setParameter("vin", car.getVin());
        List<Car> existing = query.getResultList();
        if (!existing.isEmpty()) {
            throw new DuplicateVinException("Автомобиль с VIN " + car.getVin() + " уже существует");
        }
    }

    /**
     * Обновляет существующий автомобиль
     * @param car автомобиль для обновления
     * @return обновленный автомобиль
     * @throws CarNotFoundException если автомобиль не найден
     * @throws DuplicateVinException если VIN уже существует
     * @throws ComponentInUseException если компоненты уже используются
     */
    public Car update(Car car) throws CarNotFoundException, DuplicateVinException, ComponentInUseException {
        Car existing = em.find(Car.class, car.getId());
        if (existing == null) {
            throw new CarNotFoundException("Автомобиль с ID " + car.getId() + " не найден");
        }

        if (car.getVin() != null && !car.getVin().equals(existing.getVin())) {
            checkUniqueVin(car);
        }

        if (car.getBody() != null && !car.getBody().equals(existing.getBody()) && car.getBody().isCarLinked()) {
            throw new ComponentInUseException("Кузов уже используется в другом автомобиле");
        }
        if (car.getEngine() != null && !car.getEngine().equals(existing.getEngine()) && car.getEngine().isCarLinked()) {
            throw new ComponentInUseException("Двигатель уже используется в другом автомобиле");
        }
        if (car.getTransmission() != null && !car.getTransmission().equals(existing.getTransmission()) && car.getTransmission().isCarLinked()) {
            throw new ComponentInUseException("Трансмиссия уже используется в другом автомобиле");
        }
        
        return em.merge(car);
    }

    /**
     * Удаляет автомобиль
     * @param id ID автомобиля
     * @throws CarNotFoundException если автомобиль не найден
     */
    public void delete(Long id) throws CarNotFoundException {
        Car car = em.find(Car.class, id);
        if (car == null) {
            throw new CarNotFoundException("Автомобиль с ID " + id + " не найден");
        }

        disassembleCar(id);
    }

        /**
     * Собирает автомобиль из компонентов
     * @param bodyId ID кузова
     * @param engineId ID двигателя
     * @param transmissionId ID трансмиссии
     * @return собранный автомобиль
     * @throws ComponentNotFoundException если компонент не найден
     * @throws ComponentInUseException если компонент уже используется
     * @throws DuplicateVinException если VIN уже существует
     */
    public Car assembleCar(Long bodyId, Long engineId, Long transmissionId)
            throws ComponentNotFoundException, ComponentInUseException, DuplicateVinException {
        CarBody body = carBodyService.findById(bodyId);
        Engine engine = engineService.findById(engineId);
        Transmission transmission = transmissionService.findById(transmissionId);

        if (body == null) {
            throw new ComponentNotFoundException("Кузов с ID " + bodyId + " не найден");
        }
        if (engine == null) {
            throw new ComponentNotFoundException("Двигатель с ID " + engineId + " не найден");
        }
        if (transmission == null) {
            throw new ComponentNotFoundException("Трансмиссия с ID " + transmissionId + " не найдена");
        }

        if (body.isCarLinked()) {
            throw new ComponentInUseException("Кузов уже используется в другом автомобиле");
        }
        if (engine.isCarLinked()) {
            throw new ComponentInUseException("Двигатель уже используется в другом автомобиле");
        }
        if (transmission.isCarLinked()) {
            throw new ComponentInUseException("Трансмиссия уже используется в другом автомобиле");
        }

        Car car = new Car(body, engine, transmission, new Date());

        car.setBody(body);
        car.setEngine(engine);
        car.setTransmission(transmission);

        em.persist(car);
        em.flush();

        return car;
    }

    /**
     * Разбирает автомобиль, освобождая компоненты
     * @param carId ID автомобиля
     * @throws CarNotFoundException если автомобиль не найден
     */
    public void disassembleCar(Long carId) throws CarNotFoundException {
        Car car = em.find(Car.class, carId);
        if (car == null) {
            throw new CarNotFoundException("Автомобиль с ID " + carId + " не найден");
        }
        
        CarBody body = car.getBody();
        Engine engine = car.getEngine();
        Transmission transmission = car.getTransmission();

        if (body != null) {
            body.setCar(null);
            em.merge(body);
        }
        if (engine != null) {
            engine.setCar(null);
            em.merge(engine);
        }
        if (transmission != null) {
            transmission.setCar(null);
            em.merge(transmission);
        }

        em.remove(car);
        em.flush();
    }

    public Car findById(Long id) {
        return em.find(Car.class, id);
    }

    public List<Car> findAll() {
        return em.createNamedQuery("Car.findAll", Car.class).getResultList();
    }

    public PaginationHelper<Car> findAll(PaginationParams params) {

        QuerySpecification<Car> specification = new CarSpecification(
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
