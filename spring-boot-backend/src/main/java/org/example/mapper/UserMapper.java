package org.example.mapper;

import java.util.List;
import org.example.dto.UserDto;
import org.example.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto toDto(User user);

  List<UserDto> toDtos(List<User> users);
}
