package fullstack.api.presentation.mapper;

import fullstack.api.domain.LoginResponse;
import fullstack.api.openapi.model.LoginResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {UserRoleMapper.class})
public interface LoginResponseMapper {
  LoginResponseDto toDto(LoginResponse loginResponse);
}
