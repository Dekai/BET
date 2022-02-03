package com.dk.alirr.service;

import com.dk.alirr.entity.User;
import com.dk.alirr.exception.ResourceNotFoundException;
import com.dk.alirr.repository.UserRepository;
import com.dk.alirr.service.dto.UserCreateDTO;
import com.dk.alirr.service.dto.UserDTO;
import com.dk.alirr.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getUsers(Specification<User> spec, Pageable page) {
        return userRepository.findAll(spec, page).map(userMapper::userToUserDTO);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByCarBrand(String carBrand, Pageable page) {
        return userRepository.findUsersbyCarBrand(carBrand, page).map(userMapper::userToUserDTO);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        //Encode the password
        String plainPassword = userCreateDTO.getPassword();
        userCreateDTO.setPassword(passwordEncoder.encode(plainPassword));

        User createdUser = userRepository.save(userMapper.userCreateDTOToUser(userCreateDTO));

        return userMapper.userToUserDTO(createdUser);
    }

    public void updateUser(Long id, UserDTO userDTO) {
        userRepository.findById(id)
                .map(user -> {
                    userMapper.updateUserFromDTO(userDTO, user);
                    return user;
                })
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), id));
    }

    public void deleteUser(Long id) {
        log.debug("Service - delete user {}", id);
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Service - Delete User, [id={}] not exists.", id);
            throw new ResourceNotFoundException(User.class.getSimpleName(), id);
        }
    }
}
