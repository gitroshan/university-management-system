package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    // No additional methods
}
