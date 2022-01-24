package com.metadata.schoolregistration.repository;

import com.metadata.schoolregistration.entity.UserTokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepository extends CrudRepository<UserTokenEntity, UUID> {

  Optional<UserTokenEntity> findByRefreshToken(String refreshToken);
  Optional<UserTokenEntity> deleteByUserId(UUID userId);

}

