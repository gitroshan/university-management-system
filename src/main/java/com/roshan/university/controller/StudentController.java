package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roshan.university.model.Student;
import com.roshan.university.repository.StudentRepository;

@Controller
public class StudentController {

    private Logger log = LoggerFactory.getLogger(StudentController.class);

    private @Autowired StudentRepository studentRepository;

    @GetMapping("/students/result")
    public String result(Model model) {

        List<Student> students = this.studentRepository.findAll();

        Collections.sort(students, (student1, student2) -> {
            return student1.getId().intValue() - student2.getId().intValue();
        });

        model.addAttribute("students", students);
        return "student/result";
    }

    @GetMapping("/students")
    public String index(Student student) {
        this.log.info("Loading student form {}.", student);
        return "student/index";
    }

    @PostMapping("/students")
    public String addNewStudent(@Valid Student student, BindingResult bindingResult, Model model) {

        this.log.info("Submitting student {}", student);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "student/index";
            }

            this.studentRepository.save(student);
            model.addAttribute("successMessage", "Student is saved");

            List<Student> students = this.studentRepository.findAll();

            Collections.sort(students, (student1, student2) -> {
                return student1.getId().intValue() - student2.getId().intValue();
            });

            model.addAttribute("students", students);

            return "student/result";
        } catch (Exception e) {

            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("student", "name", "Student cannot be saved.");
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("student", "email",
                        "Student with email \"" + student.getEmail() + "\" is already exists.");
            }
            bindingResult.addError(error);

            return "student/index";
        }
    }

}
