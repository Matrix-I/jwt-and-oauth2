package fullstack.api.domain;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class User {
  private final UUID id;
  private final String username;
  private final String password;
  private final List<String> roles;

  public User(UUID id, String username, String password, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = stringToListRoles(role);
  }

  public static String listRolesToString(List<String> roles) {
    return String.join(",", roles);
  }

  public static List<String> stringToListRoles(String roles) {
    return Arrays.asList(roles.split(","));
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public List<String> getRoles() {
    return roles;
  }
}
