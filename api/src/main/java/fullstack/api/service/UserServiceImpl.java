package fullstack.api.service;

import fullstack.api.config.CustomUserDetails;
import fullstack.api.config.JwtTokenProvider;
import fullstack.api.entity.UserEntity;
import fullstack.api.openapi.model.LoginRequestDto;
import fullstack.api.openapi.model.LoginResponseDto;
import fullstack.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    UserEntity userEntity =
        userRepository
            .findByUsername(loginRequestDto.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException(loginRequestDto.getUsername()));
    if (passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())) {
      return new LoginResponseDto()
          .tokenType("Bearer")
          .token(jwtTokenProvider.generateToken(new CustomUserDetails(userEntity)));
    }
    return null;
  }
}
