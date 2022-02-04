package com.dk.rr.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String perm;
    private List<CarDTO> cars;
    private Set<String> authorities;
}
