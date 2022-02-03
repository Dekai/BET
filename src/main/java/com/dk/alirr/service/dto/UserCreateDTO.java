package com.dk.alirr.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank
    @Size(min=3, max = 250)
    private String name;

    @NotBlank
    @Email
    @Size(min=3, max = 250)
    private String email;

    @NotBlank
    @Size(min=3, max = 250)
    private String password;
    private String perm;

}
