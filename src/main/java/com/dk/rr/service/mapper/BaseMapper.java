package com.dk.rr.service.mapper;

public interface BaseMapper<T, E> {
    E toEntity(T dto);
    T toDTO(E entity);
}
