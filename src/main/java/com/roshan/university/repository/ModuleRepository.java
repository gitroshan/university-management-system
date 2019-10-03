package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    // No additional methods
}
