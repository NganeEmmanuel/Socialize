package com.socialize.repository;

import com.socialize.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Set<User> findFollowingById(Long id);
    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    List<User> findFollowingByUserId(@Param("userId") Long userId);


    @Query("SELECT u FROM User u WHERE (u.username LIKE CONCAT('%', LOWER(:username), '%') OR u.name LIKE CONCAT('%', LOWER(:username), '%'))")
    List<User> findByUsernameContainingOrNameContaining(String username, Pageable pageable);
}