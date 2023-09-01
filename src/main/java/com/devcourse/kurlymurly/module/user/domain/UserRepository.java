package com.devcourse.kurlymurly.module.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);
}
