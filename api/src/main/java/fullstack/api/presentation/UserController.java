package fullstack.api.presentation;

import fullstack.api.domain.LoginResponse;
import fullstack.api.exception.ApplicationException;
import fullstack.api.openapi.api.UserApi;
import fullstack.api.openapi.model.LoginRequestDto;
import fullstack.api.openapi.model.LoginResponseDto;
import fullstack.api.presentation.mapper.LoginRequestMapper;
import fullstack.api.presentation.mapper.LoginResponseMapper;
import fullstack.api.service.UserService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final LoginRequestMapper requestMapper = Mappers.getMapper(LoginRequestMapper.class);

  private final LoginResponseMapper responseMapper = Mappers.getMapper(LoginResponseMapper.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PreAuthorize("permitAll()")
  @Override
  public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto)
      throws ApplicationException {
    LoginResponse loginResponse = userService.login(requestMapper.toDomain(loginRequestDto));
    return ResponseEntity.ok(responseMapper.toDto(loginResponse));
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Override
  public ResponseEntity<Void> test() {
    LOGGER.info("test");
    return ResponseEntity.ok().build();
  }
}
