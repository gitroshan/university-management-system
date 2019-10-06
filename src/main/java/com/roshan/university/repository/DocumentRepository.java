package com.roshan.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByName(String name);
}
