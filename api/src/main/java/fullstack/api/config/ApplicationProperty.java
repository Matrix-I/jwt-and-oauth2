package fullstack.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProperty {

  private JwtProperties jwtProperties;

  private FrontendEdge frontendEdge;

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

  public static class FrontendEdge {
    private String apiBasePath;

    private String guiBaseHref;

    public String getApiBasePath() {
      return apiBasePath;
    }

    public void setApiBasePath(String apiBasePath) {
      this.apiBasePath = apiBasePath;
    }

    public String getGuiBaseHref() {
      return guiBaseHref;
    }

    public void setGuiBaseHref(String guiBaseHref) {
      this.guiBaseHref = guiBaseHref;
    }
  }

  public JwtProperties getJwtProperties() {
    return jwtProperties;
  }

  public FrontendEdge getFrontendEdge() {
    return frontendEdge;
  }

  public void setFrontendEdge(FrontendEdge frontendEdge) {
    this.frontendEdge = frontendEdge;
  }

  public void setJwtProperties(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }
}
