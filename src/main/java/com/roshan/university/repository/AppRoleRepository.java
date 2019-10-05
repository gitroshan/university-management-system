package com.roshan.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roshan.university.model.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> findByName(String name);
}
