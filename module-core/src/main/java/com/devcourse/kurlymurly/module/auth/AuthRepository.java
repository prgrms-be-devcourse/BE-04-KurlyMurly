package com.devcourse.kurlymurly.module.auth;

import com.devcourse.kurlymurly.module.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);
}
