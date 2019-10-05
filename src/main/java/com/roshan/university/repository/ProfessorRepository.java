package com.roshan.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByEmail(String email);

    Optional<Professor> findByPhoneNumber(String phoneNumber);
}
