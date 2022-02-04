package com.dk.rr.controller;

import com.dk.rr.entity.Driver;
import com.dk.rr.service.DriverService;
import com.dk.rr.service.dto.DriverDTO;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
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
@RequestMapping("/api/drivers")
@Slf4j
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * @param spec     drivers?name=dk
     * @param pageable request URL like drivers?page=0&size=2&sort=id
     * @return
     */
    @GetMapping("")
    public ResponseEntity<List<DriverDTO>> getAll(@And({
            @Spec(path = "name", spec = Like.class),
            @Spec(path = "age", spec = Equal.class)
    }) Specification<Driver> spec, Pageable pageable) {
        log.debug("REST request - Get driver list");
        final Page<DriverDTO> driverDTOList = driverService.getDrivers(spec, pageable);
        return ResponseEntity.ok(driverDTOList.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getById(@PathVariable Long id) {
        log.debug("REST request - Get Driver by id={}", id);
        return ResponseEntity.ok(driverService.getDriverById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found")));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody DriverDTO driverDTO) {
        log.debug("REST request - Create Driver {}", driverDTO);
        driverService.createDriver(driverDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/assignCar")
    public ResponseEntity<Void> assignCar(@RequestParam("carId") String carId, @RequestParam("driverId") String driverId) {
        log.debug("REST request - Assign car={} to Driver={}", carId, driverId);
        driverService.assignCar(carId, driverId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> update(@PathVariable Long id, @RequestBody DriverDTO driverDTO) {
        log.debug("REST request - Update Driver[id={}] with value {}", id, driverDTO);
        driverDTO.setId(id);
        driverService.updateDriver(id, driverDTO);
        return ResponseEntity.ok(driverDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request - Delete Driver[id={}]", id);
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    //TODO: Add search endpoint
}
