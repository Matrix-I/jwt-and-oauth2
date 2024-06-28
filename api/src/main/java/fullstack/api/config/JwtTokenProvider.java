package fullstack.api.config;

import fullstack.api.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {
  private static final String AUTHORITIES_KEY = "authorities";

  private final ApplicationProperty applicationProperty;

  public JwtTokenProvider(ApplicationProperty applicationProperty) {
    this.applicationProperty = applicationProperty;
  }

  public String generateToken(CustomUserDetails userDetails) {
    Date now = new Date();
    Date expiryDate =
        new Date(now.getTime() + applicationProperty.getJwtProperties().getExpireTime());

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim(AUTHORITIES_KEY, userDetails.getAuthorities())
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(getKey())
        .compact();
  }

  public CustomUserDetails getUserIdFromJWT(String token) {

    Claims claims =
        Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(claims.getSubject());
    userEntity.setRole(toRoles(claims.get(AUTHORITIES_KEY, ArrayList.class)));

    return new CustomUserDetails(userEntity);
  }

  private String toRoles(List<LinkedHashMap<String, String>> authorities) {
    return authorities.stream()
        .map(authority -> authority.get("authority"))
        .collect(Collectors.joining(","));
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(authToken);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(applicationProperty.getJwtProperties().getSecret().getBytes());
  }
}
