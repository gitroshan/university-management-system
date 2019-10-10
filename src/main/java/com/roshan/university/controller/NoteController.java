package com.roshan.university.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roshan.university.exception.ProfessorNotFoundException;
import com.roshan.university.model.Course;
import com.roshan.university.model.Note;
import com.roshan.university.model.Professor;
import com.roshan.university.model.Student;
import com.roshan.university.repository.CourseRepository;
import com.roshan.university.repository.NoteRepository;
import com.roshan.university.repository.ProfessorRepository;
import com.roshan.university.repository.StudentRepository;

@Controller
public class NoteController {

    private Logger log = LoggerFactory.getLogger(NoteController.class);

    private @Autowired NoteRepository noteRepository;

    private @Autowired CourseRepository courseRepository;

    private @Autowired StudentRepository studentRepository;

    private @Autowired ProfessorRepository professorRepository;

    @GetMapping("/notes/result")
    public String result(Model model, Authentication authentication) {

        boolean isTeacher = false;
        boolean isStudent = false;

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication
                .getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if ("ROLE_TEACHER".equals(grantedAuthority.getAuthority())) {
                isTeacher = true;
            }
            if ("ROLE_STUDENT".equals(grantedAuthority.getAuthority())) {
                isStudent = true;
            }

        }

        List<Note> notes = new ArrayList<>();

        if (isTeacher) {
            notes = this.noteRepository.findByProfessorEmail(authentication.getName());
        } else if (isStudent) {
            notes = this.noteRepository.findByStudentEmail(authentication.getName());
        } else {
            notes = this.noteRepository.findAll();
        }

        Collections.sort(notes, (note1, note2) -> {
            return note1.getId().intValue() - note2.getId().intValue();
        });

        model.addAttribute("notes", notes);
        return "note/result";
    }

    @GetMapping("/notes")
    public String index(Note note, Model model) {
        this.log.info("Loading note form {}.", note);

        List<Course> courses = this.courseRepository.findAll();
        List<Student> students = this.studentRepository.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("students", students);
        return "note/index";
    }

    @PostMapping("/notes")
    public String addNewNote(@Valid Note note, BindingResult bindingResult, Model model,
            Authentication authentication) {

        this.log.info("Submitting note {}", note);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                List<Course> courses = this.courseRepository.findAll();
                List<Student> students = this.studentRepository.findAll();
                model.addAttribute("courses", courses);
                model.addAttribute("students", students);
                return "note/index";
            }

            Optional<Professor> professor = this.professorRepository.findByEmail(authentication.getName());
            if (!professor.isPresent()) {
                throw new ProfessorNotFoundException("Professor is not found with email " + authentication.getName());
            }

            note.setProfessor(professor.get());
            this.noteRepository.save(note);
            model.addAttribute("successMessage", "Note is saved");

            List<Note> notes = this.noteRepository.findAll();

            Collections.sort(notes, (note1, note2) -> {
                return note1.getId().intValue() - note2.getId().intValue();
            });

            model.addAttribute("notes", notes);

            return "note/result";
        } catch (ProfessorNotFoundException e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("note", "note", e.getMessage());
            bindingResult.addError(error);
            List<Course> courses = this.courseRepository.findAll();
            List<Student> students = this.studentRepository.findAll();
            model.addAttribute("courses", courses);
            model.addAttribute("students", students);
            return "note/index";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("note", "note", "Note cannot be saved.");
            bindingResult.addError(error);
            List<Course> courses = this.courseRepository.findAll();
            List<Student> students = this.studentRepository.findAll();
            model.addAttribute("courses", courses);
            model.addAttribute("students", students);
            return "note/index";
        }
    }

}
