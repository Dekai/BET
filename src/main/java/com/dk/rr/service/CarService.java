package com.dk.rr.service;

import com.dk.rr.entity.Car;
import com.dk.rr.exception.ResourceNotFoundException;
import com.dk.rr.repository.CarRepository;
import com.dk.rr.service.dto.CarDTO;
import com.dk.rr.service.mapper.CarMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Transactional(readOnly = true)
    public Page<CarDTO> getCars(Specification<Car> spec, Pageable page) {
        return carRepository.findAll(spec, page).map(carMapper::carToCarDTO);
    }

    @Transactional(readOnly = true)
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public void createCar(CarDTO carDTO) {
        carRepository.save(carMapper.carDTOToCar(carDTO));
    }

    public void updateCar(Long id, CarDTO carDTO) {
        carRepository.findById(id)
                .map(car -> {
                    carMapper.carDTOToCar(carDTO);
                    return car;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Car.class.getSimpleName(), id));
    }

    public void deleteCar(Long id) {
        log.debug("Service - delete car {}", id);
        try {
            carRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Service - Delete Car, [id={}] not exists.", id);
            throw new ResourceNotFoundException(Car.class.getSimpleName(), id);
        }
    }

    public Page<CarDTO> getCarsByDriverName(String driverName, Pageable pageable) {
        return carRepository.findCarsByDriverName(driverName, pageable).map(carMapper::carToCarDTO);
    }
}
