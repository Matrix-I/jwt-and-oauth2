package fullstack.api.config;

import fullstack.api.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
    //    DefaultOidcUser userPrincipal = (DefaultOidcUser) authentication.getPrincipal();
    //    Date expiryDate =
    //
    // Date.from(Instant.now().plus(this.dccApplicationProperty.getSecurity().getTokenDuration()));
    //    Map<String, Object> claims = new HashMap<>();
    //    claims.put(LoginUserConverter.NAME, userPrincipal.getFullName());
    //    claims.put(LoginUserConverter.EMAIL, userPrincipal.getEmail());
    //    claims.put(LoginUserConverter.PREFERRED_USERNAME, userPrincipal.getPreferredUsername());
    //    claims.put(LoginUserConverter.GROUPS,
    // userPrincipal.getClaims().get(LoginUserConverter.GROUPS));
    //
    //    return Jwts.builder()
    //            .setSubject(userPrincipal.getEmail())
    //            .setClaims(claims)
    //            .setIssuedAt(new Date())
    //            .setExpiration(expiryDate)
    //            .signWith(
    //                    Keys.hmacShaKeyFor(
    //
    // this.dccApplicationProperty.getSecurity().getTokenSecret().getBytes()),
    //                    SignatureAlgorithm.HS512)
    //            .compact();
    return ";";
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

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(applicationProperty.getJwtProperties().getSecret().getBytes());
  }
}
