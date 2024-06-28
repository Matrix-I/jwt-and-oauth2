package fullstack.api.service;

import fullstack.api.openapi.model.LoginRequestDto;
import fullstack.api.openapi.model.LoginResponseDto;

public interface UserService {
  LoginResponseDto login(LoginRequestDto loginRequestDto);
}
