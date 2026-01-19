package com.prodapt.license_tracker.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prodapt.license_tracker.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
