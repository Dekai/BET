package com.dk.alirr.service.mapper;

import com.dk.alirr.entity.Authority;
import com.dk.alirr.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends BaseMapper<AuthorityDTO, Authority> {
}
