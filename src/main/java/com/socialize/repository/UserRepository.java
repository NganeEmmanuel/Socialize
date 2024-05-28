package com.socialize.repository;

import com.socialize.dto.UserDTO;
import com.socialize.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String email);

    Set<User> findFollowingById(Long id);

    @Query("SELECT u FROM User u WHERE (u.username LIKE CONCAT('%', LOWER(:username), '%') OR u.name LIKE CONCAT('%', LOWER(:username), '%'))")
    List<User> findByUsernameContainingOrNameContaining(String username, Pageable pageable);
}