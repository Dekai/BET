package com.dk.alirr.service.mapper;

import com.dk.alirr.entity.Car;
import com.dk.alirr.entity.Driver;
import com.dk.alirr.service.dto.CarDTO;
import com.dk.alirr.service.dto.DriverDTO;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CarMapper{

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(target = "drivers", qualifiedByName = "DriverSetIgnoreCars")
    })
    CarDTO carToCarDTO(Car car);

    @Mapping(source = "userId", target = "user.id")
    Car carDTOToCar(CarDTO carDTO);

    @Named("DriverIgnoreCars")
    @Mapping(target = "cars", ignore = true)
    DriverDTO driverToDriverDTOIgnoreCars(Driver driver);

    @Named("DriverSetIgnoreCars")
    @IterableMapping(qualifiedByName = "DriverIgnoreCars")
    Set<DriverDTO> toDriverDTOSetIgnoreCars(Set<Driver> drivers);
}
