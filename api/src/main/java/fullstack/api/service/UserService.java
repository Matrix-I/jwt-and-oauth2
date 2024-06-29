package fullstack.api.service;

import fullstack.api.domain.LoginRequest;
import fullstack.api.domain.LoginResponse;
import fullstack.api.exception.ApplicationException;

public interface UserService {
  LoginResponse login(LoginRequest loginRequest) throws ApplicationException;
}
