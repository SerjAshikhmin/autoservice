package com.senla.courses.autoservice.dto.mappers;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.UserDto;
import com.senla.courses.autoservice.model.domain.Garage;
import com.senla.courses.autoservice.model.security.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring", uses = RoleMapper.class)
public interface UserMapper {
    @FullMapping
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto user);

    @IterableMapping(qualifiedBy = FullMapping.class)
    @FullMapping
    List<UserDto> userListToUserDtoList(List<User> list);
}
