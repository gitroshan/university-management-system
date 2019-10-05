package com.roshan.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Optional<Module> findByName(String name);
}
