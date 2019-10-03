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

import com.roshan.university.model.Classroom;
import com.roshan.university.repository.ClassroomRepository;

@Controller
public class ClassroomController {

    private Logger log = LoggerFactory.getLogger(ClassroomController.class);

    private @Autowired ClassroomRepository classroomRepository;

    @GetMapping("/classrooms/result")
    public String result(Model model) {

        List<Classroom> classrooms = this.classroomRepository.findAll();

        Collections.sort(classrooms, (classroom1, classroom2) -> {
            return classroom1.getId().intValue() - classroom2.getId().intValue();
        });

        model.addAttribute("classrooms", classrooms);
        return "classroom/result";
    }

    @GetMapping("/classrooms")
    public String index(Classroom classroom) {
        this.log.info("Loading classroom form {}.", classroom);
        return "classroom/index";
    }

    @PostMapping("/classrooms")
    public String addNewClassroom(@Valid Classroom classroom, BindingResult bindingResult, Model model) {

        this.log.info("Submitting classroom {}", classroom);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "classroom/index";
            }

            this.classroomRepository.save(classroom);
            model.addAttribute("successMessage", "Classroom is saved");

            List<Classroom> classrooms = this.classroomRepository.findAll();

            Collections.sort(classrooms, (classroom1, classroom2) -> {
                return classroom1.getId().intValue() - classroom2.getId().intValue();
            });

            model.addAttribute("classrooms", classrooms);

            return "classroom/result";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("classroom", "name", "Classroom cannot be saved.");
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("classroom", "name",
                        "Classroom with name \"" + classroom.getName() + "\" is already exists.");
            } else if (e instanceof NumberFormatException) {
                error = new FieldError("classroom", "capacity", "Please provide a valid number for Capacity");
            }
            bindingResult.addError(error);

            return "classroom/index";
        }
    }

}
