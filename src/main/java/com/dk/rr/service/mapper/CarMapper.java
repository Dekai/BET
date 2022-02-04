package com.dk.rr.service.mapper;

import com.dk.rr.entity.Car;
import com.dk.rr.entity.Driver;
import com.dk.rr.service.dto.CarDTO;
import com.dk.rr.service.dto.DriverDTO;
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
