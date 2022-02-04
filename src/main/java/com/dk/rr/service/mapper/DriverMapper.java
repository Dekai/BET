package com.dk.rr.service.mapper;

import com.dk.rr.entity.Car;
import com.dk.rr.entity.Driver;
import com.dk.rr.service.dto.CarDTO;
import com.dk.rr.service.dto.DriverDTO;
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
