package com.dk.rr.service.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class DriverDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer age;
    private Set<CarDTO> cars;

}
