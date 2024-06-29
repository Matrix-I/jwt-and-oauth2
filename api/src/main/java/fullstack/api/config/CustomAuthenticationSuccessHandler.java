package fullstack.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenProvider tokenProvider;

  public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
    this.tokenProvider = jwtTokenProvider;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    String token = tokenProvider.createToken(authentication);
    Duration tokenCookieDuration = Duration.ofMillis(3600);
    Cookies.addCookie(
        response,
        Cookies.TOKEN_COOKIE_NAME,
        token,
        Math.toIntExact(tokenCookieDuration.getSeconds()),
        true);
    this.getRedirectStrategy().sendRedirect(request, response, "/auth/callback");
  }
}
