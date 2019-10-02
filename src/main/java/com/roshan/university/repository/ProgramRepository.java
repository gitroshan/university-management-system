package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    // No additional methods
}
