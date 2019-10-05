package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roshan.university.model.Course;
import com.roshan.university.model.Program;
import com.roshan.university.repository.CourseRepository;
import com.roshan.university.repository.ProgramRepository;

@Controller
public class CourseController {

    private Logger log = LoggerFactory.getLogger(CourseController.class);

    private @Autowired CourseRepository courseRepository;

    private @Autowired ProgramRepository programRepository;

    @GetMapping("/courses/result")
    public String result(Model model) {

        List<Course> courses = this.courseRepository.findAll();

        Collections.sort(courses, (course1, course2) -> {
            return course1.getId().intValue() - course2.getId().intValue();
        });

        model.addAttribute("courses", courses);
        return "course/result";
    }

    @GetMapping("/courses")
    public String index(Course course, Model model) {
        this.log.info("Loading course form {}.", course);

        List<Program> programs = this.programRepository.findAll();
        model.addAttribute("programs", programs);
        return "course/index";
    }

    @PostMapping("/courses")
    public String addNewCourse(@Valid Course course, BindingResult bindingResult, Model model) {

        this.log.info("Submitting course {}", course);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                List<Program> programs = this.programRepository.findAll();
                model.addAttribute("programs", programs);
                return "course/index";
            }

            Optional<Course> courseOptional = this.courseRepository.findByName(course.getName());
            if (courseOptional.isPresent()) {
                this.log.error("Course with name \"{}\" is already exists.", course.getName());
                ObjectError error = new FieldError("course", "name",
                        "Course with name \"" + course.getName() + "\" is already exists.");

                bindingResult.addError(error);

                return "course/index";
            }
            this.courseRepository.save(course);
            model.addAttribute("successMessage", "Course is saved");

            List<Course> courses = this.courseRepository.findAll();

            Collections.sort(courses, (course1, course2) -> {
                return course1.getId().intValue() - course2.getId().intValue();
            });

            model.addAttribute("courses", courses);

            return "course/result";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("course", "name", "Course cannot be saved.");
            bindingResult.addError(error);

            List<Program> programs = this.programRepository.findAll();
            model.addAttribute("programs", programs);

            return "course/index";
        }
    }

}
