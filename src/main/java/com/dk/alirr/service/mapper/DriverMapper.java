package com.dk.alirr.service.mapper;

import com.dk.alirr.entity.Car;
import com.dk.alirr.entity.Driver;
import com.dk.alirr.service.dto.CarDTO;
import com.dk.alirr.service.dto.DriverDTO;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring",
uses = {CarMapper.class}
)
public interface DriverMapper {

    @Mapping(target = "cars", qualifiedByName = "CarSetIgnoreDrivers")
    DriverDTO driverToDriverDTO(Driver driver);

    Driver driverDTOToDriver(DriverDTO driverDTO);

    @Named("CarIgnoreDrivers")
    @Mappings({
        @Mapping(source = "user.id", target = "userId"),
        @Mapping(target = "drivers", ignore = true)
    })
    CarDTO carToCarDTOIgnoreDriver(Car car);

    @Named("CarSetIgnoreDrivers")
    @IterableMapping(qualifiedByName = "CarIgnoreDrivers")
    Set<CarDTO> toCarDTOSetIgnoreDrivers(Set<Car> car);
}
