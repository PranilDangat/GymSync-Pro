package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}