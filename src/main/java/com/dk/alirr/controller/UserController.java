package com.dk.alirr.controller;

import com.dk.alirr.entity.User;
import com.dk.alirr.security.AuthoritiesConstants;
import com.dk.alirr.security.SecurityUtils;
import com.dk.alirr.service.UserService;
import com.dk.alirr.service.dto.UserCreateDTO;
import com.dk.alirr.service.dto.UserDTO;
import com.dk.alirr.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * @param spec     users?name=dk
     * @param pageable request URL like users?page=0&size=2&sort=id
     * @return
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<UserDTO>> getAll(@And({
            @Spec(path = "name", spec = Like.class),
            @Spec(path = "email", spec = Like.class)
    }) Specification<User> spec, Pageable pageable) {
        log.debug("REST request - Get user list");

        Optional<Long> userId = SecurityUtils.getCurrentUserId();
        userId.ifPresent(userIdValue -> MDC.put("USER_ID", String.valueOf(userIdValue)));

        final Page<UserDTO> userDTOList = userService.getUsers(spec, pageable);

        return ResponseEntity.ok(userDTOList.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        log.debug("REST request - Get User by id={}", id);
        return ResponseEntity.ok(userService.getUserById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @GetMapping("/getByCarBrand/{carBrand}")
    public ResponseEntity<List<UserDTO>> getByCarBrand(@PathVariable String carBrand, Pageable pageable) {
        log.debug("REST request - Get User by carBrand={}", carBrand);
        Page<UserDTO> userDTOList = userService.getUsersByCarBrand(carBrand, pageable);
        return ResponseEntity.ok(userDTOList.getContent());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        log.debug("REST request - Create User {}", userMapper.userCreateDTOToUserDTO(userCreateDTO));
        UserDTO user = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.debug("REST request - Update User[id={}] with value {}", id, userDTO);
        userDTO.setId(id);
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request - Delete User[id={}]", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/updateWithParamterMap")
//    public String updateWithParameterMap(@RequestParam Map<String,String> allParams) {
//        return "Parameters are " + allParams.entrySet();
//    }

}
