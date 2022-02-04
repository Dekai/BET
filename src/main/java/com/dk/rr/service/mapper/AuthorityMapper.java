package com.dk.rr.service.mapper;

import com.dk.rr.entity.Authority;
import com.dk.rr.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends BaseMapper<AuthorityDTO, Authority> {
}
