package fullstack.api.presentation.mapper;

import fullstack.api.domain.UserRole;
import fullstack.api.openapi.model.UserRoleDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserRoleMapper {
  UserRole toDomain(UserRoleDto dto);

  UserRoleDto toDto(UserRole domain);
}
