package fullstack.api.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Stream;

public final class Cookies {

  public static final String TOKEN_COOKIE_NAME = "app_token";

  private Cookies() {
    throw new IllegalStateException("Utility class");
  }

  public static void addCookie(
      HttpServletResponse response, String name, String value, int maxAge, String sameSiteCookie) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setAttribute("SameSite", sameSiteCookie);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  public static void addCookie(
      HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setHttpOnly(httpOnly);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  public static void deleteCookie(
      HttpServletRequest request, HttpServletResponse response, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          cookie.setValue("");
          cookie.setPath("/");
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        }
      }
    }
  }

  public static Optional<String> getCookie(HttpServletRequest request, String name) {
    return Optional.ofNullable(request.getCookies())
        .flatMap(
            cookies ->
                Stream.of(cookies).filter(cookie -> cookie.getName().equals(name)).findFirst())
        .map(Cookie::getValue);
  }

  public static String encodeCookie(String cookieValue) {
    return Base64.getEncoder().encodeToString(cookieValue.getBytes());
  }

  public static String decodeCookie(String encodedCookieValue) {
    return new String(Base64.getDecoder().decode(encodedCookieValue.getBytes()));
  }
}
