package fullstack.api.domain;

import java.util.List;
import java.util.UUID;

public class LoginResponse {
  private final UUID id;
  private final String username;
  private final List<UserRole> roles;
  private final String token;

  public LoginResponse(UUID id, String username, String role, String token) {
    this.id = id;
    this.username = username;
    this.roles = User.stringToListRoles(role).stream().map(UserRole::fromValue).toList();
    this.token = token;
  }

  public LoginResponse(String username, List<UserRole> roles) {
    this.id = null;
    this.token = null;
    this.username = username;
    this.roles = roles;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public List<UserRole> getRoles() {
    return roles;
  }

  public String getToken() {
    return token;
  }
}
