package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.ClassroomSession;

@Repository
public interface SessionRepository extends JpaRepository<ClassroomSession, Long> {
    // No additional methods
}
