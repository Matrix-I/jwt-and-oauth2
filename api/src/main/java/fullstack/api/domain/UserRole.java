package fullstack.api.domain;

import fullstack.api.exception.ConsistencyException;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public enum UserRole {
  ADMIN("ROLE_ADMIN"),

  USER("ROLE_USER");

  private final String value;

  UserRole(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static UserRole fromValue(String name) {
    String value = name.substring("ROLE_".length());
    return Stream.of(UserRole.values())
        .filter(role -> StringUtils.equalsIgnoreCase(role.toString(), value))
        .findFirst()
        .orElseThrow(
            () -> new ConsistencyException(String.format("Unable find Role for name '%s'.", name)));
  }
}
