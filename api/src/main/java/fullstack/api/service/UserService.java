package fullstack.api.service;

import fullstack.api.domain.LoginRequest;
import fullstack.api.domain.LoginResponse;
import fullstack.api.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
  LoginResponse login(LoginRequest loginRequest) throws ApplicationException;

  LoginResponse login(HttpServletRequest request) throws ApplicationException;
}
