package org.lsearch.LRequest.repositories;

import org.lsearch.LRequest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByProviderId(String providerId);
}
