package fullstack.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenProvider tokenProvider;
  private final ApplicationProperty applicationProperty;

  public CustomAuthenticationSuccessHandler(
      JwtTokenProvider jwtTokenProvider, ApplicationProperty applicationProperty) {
    this.tokenProvider = jwtTokenProvider;
    this.applicationProperty = applicationProperty;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    String token = tokenProvider.createToken(authentication);
    Cookies.addCookie(
        response,
        Cookies.TOKEN_COOKIE_NAME,
        token,
        Math.toIntExact(applicationProperty.getJwtProperties().getExpireTime()),
        true);
    this.getRedirectStrategy().sendRedirect(request, response, "/auth/callback");
  }
}
