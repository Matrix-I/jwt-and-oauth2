package fullstack.api.config;

import fullstack.api.domain.LoginResponse;
import fullstack.api.domain.UserRole;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class LoginUserConverter {

  private LoginUserConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static LoginResponse toLoginResponse(Map<String, Object> claims) {
    List<String> roles =
        (List<String>)
            claims.getOrDefault(JwtTokenProvider.AUTHORITIES_KEY, Collections.emptyList());
    List<UserRole> userRoles = roles.stream().map(UserRole::fromValue).toList();
    return new LoginResponse(
        claims.getOrDefault(OAuth2Attributes.EMAIL, StringUtils.EMPTY).toString(), null, userRoles);
  }
}
