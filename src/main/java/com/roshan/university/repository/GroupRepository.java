package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // No additional methods
}
