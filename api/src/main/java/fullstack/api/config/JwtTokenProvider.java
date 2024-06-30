package fullstack.api.config;

import fullstack.api.domain.LoginResponse;
import fullstack.api.domain.User;
import fullstack.api.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenProvider {

  public static final String AUTHORITIES_KEY = "authorities";

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

  private final ApplicationProperty applicationProperty;

  public JwtTokenProvider(ApplicationProperty applicationProperty) {
    this.applicationProperty = applicationProperty;
  }

  public String generateToken(User user) {
    Date now = new Date();
    Date expiryDate =
        new Date(now.getTime() + applicationProperty.getJwtProperties().getExpireTime());

    return Jwts.builder()
        .subject(user.getUsername())
        .claim(AUTHORITIES_KEY, user.getRoles())
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(getKey())
        .compact();
  }

  public String createToken(Authentication authentication) {
    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    OAuth2User userPrincipal = oauthToken.getPrincipal();
    Date now = new Date();
    Date expiryDate =
        new Date(now.getTime() + applicationProperty.getJwtProperties().getExpireTime());

    String name = userPrincipal.getAttribute(OAuth2Attributes.NAME);
    String email = userPrincipal.getAttribute(OAuth2Attributes.EMAIL);
    Map<String, Object> claims = new HashMap<>();
    claims.put(OAuth2Attributes.NAME, name);
    claims.put(OAuth2Attributes.EMAIL, email);
    claims.put(AUTHORITIES_KEY, List.of("ROLE_USER"));

    return Jwts.builder()
        .subject(Optional.ofNullable(email).orElse(name))
        .claims(claims)
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(getKey())
        .compact();
  }

  public Optional<Claims> validateTokenThenExtract(String authToken) {
    try {
      return Optional.of(
          Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(authToken).getPayload());
    } catch (MalformedJwtException ex) {
      LOGGER.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      LOGGER.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      LOGGER.error("JWT claims string is empty.");
    }
    return Optional.empty();
  }

  public LoginResponse parseTokenToLoginResponse(String token) {
    Claims claims =
        Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    String name = (String) claims.get(OAuth2Attributes.NAME);
    String email = (String) claims.getOrDefault(OAuth2Attributes.EMAIL, "");
    List<String> roles =
        (List<String>) claims.getOrDefault(AUTHORITIES_KEY, Collections.emptyList());
    return new LoginResponse(
        Optional.of(email).filter(StringUtils::hasText).orElse(name),
        token,
        roles.stream().map(UserRole::fromValue).toList());
  }

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(applicationProperty.getJwtProperties().getSecret().getBytes());
  }
}
