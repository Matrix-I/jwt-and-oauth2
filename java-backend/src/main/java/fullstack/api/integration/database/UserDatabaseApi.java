package fullstack.api.integration.database;

import fullstack.api.domain.User;
import fullstack.api.exception.EntityNotFoundApplicationException;
import fullstack.api.jpastubs.api.UserRepository;
import fullstack.api.jpastubs.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDatabaseApi {

  private final UserRepository userRepository;

  public UserDatabaseApi(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) throws EntityNotFoundApplicationException {
    UserEntity entity =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new EntityNotFoundApplicationException("Username or password not match!"));

    return UserEntityMapper.mapToDomain(entity);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public User save(User user) {
    UserEntity created = userRepository.save(UserEntityMapper.mapToEntity(user));
    return UserEntityMapper.mapToDomain(created);
  }
}
