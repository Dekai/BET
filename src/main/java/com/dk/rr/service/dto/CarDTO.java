package com.dk.rr.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class CarDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String brand;
    private String model;
    private Double price;
    private String description;
    private Long userId;
    private Set<DriverDTO> drivers;
}