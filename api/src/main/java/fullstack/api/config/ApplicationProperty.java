package fullstack.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProperty {

  private JwtProperties jwtProperties;

  @Getter
  @Setter
  public static class JwtProperties {
    private String secret;
    private long expireTime;
  }
}
