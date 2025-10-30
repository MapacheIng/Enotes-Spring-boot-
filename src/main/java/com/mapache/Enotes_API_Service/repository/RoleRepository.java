package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}