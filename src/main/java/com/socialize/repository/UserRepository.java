package com.socialize.repository;

import com.socialize.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    Set<User> findFollowingByUserId(@Param("userId") Long userId);
}