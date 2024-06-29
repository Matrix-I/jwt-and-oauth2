package fullstack.api.presentation.mapper;

import fullstack.api.domain.LoginRequest;
import fullstack.api.openapi.model.LoginRequestDto;
import org.mapstruct.Mapper;

@Mapper
public interface LoginRequestMapper {
  LoginRequest toDomain(LoginRequestDto dto);
}
