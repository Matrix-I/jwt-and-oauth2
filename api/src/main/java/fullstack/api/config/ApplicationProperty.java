package fullstack.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProperty {

  private JwtProperties jwtProperties;

  public static class JwtProperties {
    private String secret;
    private long expireTime;

    public String getSecret() {
      return secret;
    }

    public void setSecret(String secret) {
      this.secret = secret;
    }

    public long getExpireTime() {
      return expireTime;
    }

    public void setExpireTime(long expireTime) {
      this.expireTime = expireTime;
    }
  }

  public JwtProperties getJwtProperties() {
    return jwtProperties;
  }

  public void setJwtProperties(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }
}
