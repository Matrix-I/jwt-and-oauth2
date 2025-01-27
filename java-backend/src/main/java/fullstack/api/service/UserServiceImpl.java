package fullstack.api.service;

import fullstack.api.config.Cookies;
import fullstack.api.config.JwtTokenProvider;
import fullstack.api.domain.LoginRequest;
import fullstack.api.domain.LoginResponse;
import fullstack.api.domain.User;
import fullstack.api.exception.ApplicationException;
import fullstack.api.exception.ConsistencyException;
import fullstack.api.integration.database.UserDatabaseApi;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDatabaseApi userDatabaseApi;

  public UserServiceImpl(
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider,
      UserDatabaseApi userDatabaseApi) {
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDatabaseApi = userDatabaseApi;
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) throws ApplicationException {
    User user = userDatabaseApi.findByUsername(loginRequest.getUsername());

    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      return new LoginResponse(
          user.getId(),
          user.getUsername(),
          User.listRolesToString(user.getRoles()),
          jwtTokenProvider.generateToken(user));
    }

    return null;
  }

  @Override
  public LoginResponse login(HttpServletRequest request) {
    Optional<String> token = Cookies.getCookie(request, Cookies.TOKEN_COOKIE_NAME);
    if (token.isPresent()) {
      return jwtTokenProvider.parseTokenToLoginResponse(token.get());
    }
    throw new ConsistencyException("Invalid Request. Please check login firstly.");
  }
}
