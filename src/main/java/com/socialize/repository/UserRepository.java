package com.socialize.repository;

import com.socialize.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String email);

    Set<User> findFollowingById(Long id);

    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    List<User> findFollowingByUserId(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE (u.username LIKE CONCAT('%', LOWER(:username), '%') OR u.name LIKE CONCAT('%', LOWER(:username), '%'))")
    List<User> findByUsernameContainingOrNameContaining(String username, Pageable pageable);
}
