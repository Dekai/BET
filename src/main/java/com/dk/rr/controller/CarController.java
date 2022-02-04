package com.dk.rr.controller;

import com.dk.rr.entity.Car;
import com.dk.rr.service.CarService;
import com.dk.rr.service.dto.CarDTO;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     *
     * @param spec cars?name=dk
     * @param pageable request URL like cars?page=0&size=2&sort=id
     * @return
     */
    @GetMapping("")
    public ResponseEntity<List<CarDTO>> getAll(@And({
            @Spec(path = "brand", spec = Like.class),
            @Spec(path = "model", spec = Like.class)
    }) Specification<Car> spec, Pageable pageable) {
        log.debug("REST request - Get car list");
        final Page<CarDTO> carDTOList = carService.getCars(spec, pageable);
        return ResponseEntity.ok(carDTOList.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Long id) {
        log.debug("REST request - Get Car by id={}", id);
        return ResponseEntity.ok(carService.getCarById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found")));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody CarDTO carDTO) {
        log.debug("REST request - Create Car {}", carDTO);
        carService.createCar(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> update(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        log.debug("REST request - Update Car[id={}] with value {}", id, carDTO);
        carDTO.setId(id);
        carService.updateCar(id, carDTO);
        return ResponseEntity.ok(carDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request - Delete Car[id={}]", id);
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByDriverName/{driverName}")
    public ResponseEntity<List<CarDTO>> getByDriverName(@PathVariable String driverName, Pageable pageable) {
        log.debug("REST request - Get Car by driverName={}", driverName);
        Page<CarDTO> userDTOList = carService.getCarsByDriverName(driverName, pageable);
        return ResponseEntity.ok(userDTOList.getContent());
    }
}
