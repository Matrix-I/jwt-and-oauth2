package fullstack.api.jpastubs.api;

import fullstack.api.jpastubs.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByUsername(String username);

  boolean existsByUsername(String username);
}
