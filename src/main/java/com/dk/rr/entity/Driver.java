package com.dk.rr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "driver")
@Entity
@Getter
@Setter
public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "driver_car",
            joinColumns = {@JoinColumn(name = "driver_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "car_id", referencedColumnName = "id")})
    private Set<Car> cars = new HashSet<>();

    public void addCar(Car car) {
        this.getCars().add(car);
        car.getDrivers().add(this);
    }

    public void removeCar(Car car) {
        this.getCars().remove(car);
        car.getDrivers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return getAge() == driver.getAge() && getName().equals(driver.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge());
    }

}
