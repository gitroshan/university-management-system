package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // No additional methods
}
