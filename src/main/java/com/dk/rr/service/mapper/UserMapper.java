package com.dk.rr.service.mapper;

import com.dk.rr.entity.Authority;
import com.dk.rr.entity.User;
import com.dk.rr.service.dto.UserCreateDTO;
import com.dk.rr.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {CarMapper.class, AuthorityMapper.class}
)
public interface UserMapper {

    User userCreateDTOToUser(UserCreateDTO userCreateDTO);

    UserDTO userCreateDTOToUserDTO(UserCreateDTO userCreateDTO);

    User userDTOToUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);

    default String fromAuthority(Authority authority) {
        return authority == null ? null : authority.getName();
    }

    default Authority fromStringToAuthority(String name) {
        Authority authority = new Authority();
        authority.setName(name);
        return authority;
    }

    @Mapping(target = "id", ignore = true)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);
}
