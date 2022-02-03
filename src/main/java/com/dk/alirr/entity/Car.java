package com.dk.alirr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "car")
@Entity
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cars")
    private Set<Driver> drivers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return Objects.equals(brand, ((Car) o).brand) && Objects.equals(model, ((Car) o).model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrand(), getModel());
    }

}
