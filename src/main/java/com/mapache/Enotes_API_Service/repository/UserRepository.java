package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
}
