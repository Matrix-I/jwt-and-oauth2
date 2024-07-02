package fullstack.api.integration.database;

import fullstack.api.domain.User;
import fullstack.api.jpastubs.entity.UserEntity;

public class UserEntityMapper {

  private UserEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  static User mapToDomain(UserEntity entity) {
    return new User(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getRole());
  }

  static UserEntity mapToEntity(User user) {
    UserEntity entity = new UserEntity();
    entity.setId(user.getId());
    entity.setUsername(user.getUsername());
    entity.setRole(String.join(",", user.getRoles()));
    return entity;
  }
}
