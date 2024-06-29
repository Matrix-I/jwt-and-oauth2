package fullstack.api.presentation;

import fullstack.api.config.Cookies;
import fullstack.api.domain.LoginResponse;
import fullstack.api.exception.ApplicationException;
import fullstack.api.openapi.api.UserApi;
import fullstack.api.openapi.model.LoginRequestDto;
import fullstack.api.openapi.model.LoginResponseDto;
import fullstack.api.presentation.mapper.LoginRequestMapper;
import fullstack.api.presentation.mapper.LoginResponseMapper;
import fullstack.api.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

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

  @GetMapping("/auth/callback")
  public ResponseEntity<LoginResponseDto> githubCallback(HttpServletRequest request)
      throws ApplicationException {
    String token =
        Arrays.stream(request.getCookies())
            .filter(t -> t.getName().equals(Cookies.TOKEN_COOKIE_NAME))
            .findFirst()
            .map(Cookie::getValue)
            .orElse("");
    LoginResponse loginResponse = userService.login(token);
    return ResponseEntity.ok(responseMapper.toDto(loginResponse));
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Hello This is a test!");
  }

  @GetMapping("/demo")
  public ResponseEntity<String> demo() {
    return ResponseEntity.ok("Hello World!");
  }
}
