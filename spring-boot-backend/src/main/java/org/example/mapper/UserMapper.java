package org.example.mapper;

import org.example.dto.UserCreateDTO;
import org.example.dto.UserDTO;
import org.example.dto.UserUpdateDTO;
import org.example.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDTO toDTO(User user);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserCreateDTO createDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  void updateEntityFromDTO(UserUpdateDTO updateDTO, @MappingTarget User user);
}
