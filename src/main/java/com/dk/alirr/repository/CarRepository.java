package com.dk.alirr.repository;

import com.dk.alirr.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    @Query("select c from Car c inner join c.drivers d where d.name = :driverName")
    Page<Car> findCarsByDriverName(@Param("driverName") String driverName, Pageable pageable);
}
