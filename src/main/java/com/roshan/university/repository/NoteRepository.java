package com.roshan.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roshan.university.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByStudentEmail(String email);

    List<Note> findByProfessorEmail(String email);
}
