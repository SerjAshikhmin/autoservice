package com.senla.courses.autoservice.dto.mappers;

import com.senla.courses.autoservice.dto.RoleDto;
import com.senla.courses.autoservice.model.security.Role;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface RoleMapper {

    @FullMapping
    RoleDto roleToRoleDto(Role role);
    Role roleDtoToRole(RoleDto role);

    @IterableMapping(qualifiedBy = FullMapping.class)
    @FullMapping
    List<RoleDto> roleListToRoleDtoList(List<Role> list);
}
