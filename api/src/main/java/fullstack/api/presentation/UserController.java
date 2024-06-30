package fullstack.api.presentation;

import fullstack.api.domain.LoginResponse;
import fullstack.api.exception.ApplicationException;
import fullstack.api.openapi.api.UserApi;
import fullstack.api.openapi.model.LoginRequestDto;
import fullstack.api.openapi.model.LoginResponseDto;
import fullstack.api.presentation.mapper.LoginRequestMapper;
import fullstack.api.presentation.mapper.LoginResponseMapper;
import fullstack.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

  private final LoginRequestMapper requestMapper = Mappers.getMapper(LoginRequestMapper.class);

  private final LoginResponseMapper responseMapper = Mappers.getMapper(LoginResponseMapper.class);

  private final UserService userService;
  private final HttpServletRequest request;

  public UserController(UserService userService, HttpServletRequest request) {
    this.userService = userService;
    this.request = request;
  }

  @PreAuthorize("permitAll()")
  @Override
  public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto)
      throws ApplicationException {
    LoginResponse loginResponse = userService.login(requestMapper.toDomain(loginRequestDto));
    return ResponseEntity.ok(responseMapper.toDto(loginResponse));
  }

  @Override
  public ResponseEntity<LoginResponseDto> getToken() throws ApplicationException {
    LoginResponse loginResponse = userService.login(request);

    return ResponseEntity.ok(responseMapper.toDto(loginResponse));
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/auth/callback")
  public ResponseEntity<Void> githubCallback() {
    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, "http://localhost:4200")
        .build();
  }
}
