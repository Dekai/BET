package com.dk.alirr.service;

import com.dk.alirr.entity.Driver;
import com.dk.alirr.exception.DkServiceException;
import com.dk.alirr.exception.ResourceNotFoundException;
import com.dk.alirr.repository.DriverRepository;
import com.dk.alirr.service.dto.DriverDTO;
import com.dk.alirr.service.mapper.DriverMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final CarService carService;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper, CarService carService) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.carService = carService;
    }

    @Transactional(readOnly = true)
    public Page<DriverDTO> getDrivers(Specification<Driver> spec, Pageable page) {
        return driverRepository.findAll(spec, page).map(driverMapper::driverToDriverDTO);
    }

    @Transactional(readOnly = true)
    public Optional<Driver> getDriverById(Long id) {
        return driverRepository.findById(id);
    }

    public void createDriver(DriverDTO driverDTO) {
        driverRepository.save(driverMapper.driverDTOToDriver(driverDTO));
    }

    public void updateDriver(Long id, DriverDTO driverDTO) {
        driverRepository.findById(id)
                .map(driver -> {
                    driverMapper.driverDTOToDriver(driverDTO);
                    return driver;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Driver.class.getSimpleName(), id));
    }

    public void deleteDriver(Long id) {
        log.debug("Service - delete driver {}", id);
        try {
            driverRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Service - Delete Driver, [id={}] not exists.", id);
            throw new ResourceNotFoundException(Driver.class.getSimpleName(), id);
        }
    }

    public void assignCar(String carIdstr, String driverIdstr) {
        log.debug("Service - Assign car={} to Driver={}", carIdstr, driverIdstr);

        long carId = Long.parseLong(carIdstr);
        long driverId = Long.parseLong(driverIdstr);

        if (ObjectUtils.defaultIfNull(carId, 0L) == 0 || ObjectUtils.defaultIfNull(driverId, 0L) == 0) {
            throw new DkServiceException("Required CarId or driverId are empty", "server.invalid.parameter");
        }

        driverRepository.findById(driverId)
                .ifPresent(driver ->
                        carService.getCarById(carId)
                                .ifPresent(car ->
                                        driver.addCar(car)
                                )
                );
    }
}
