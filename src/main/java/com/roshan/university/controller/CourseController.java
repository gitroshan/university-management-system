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

import com.roshan.university.model.Course;
import com.roshan.university.repository.CourseRepository;

@Controller
public class CourseController {

    private Logger log = LoggerFactory.getLogger(CourseController.class);

    private @Autowired CourseRepository courseRepository;

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
    public String index(Course course) {
        this.log.info("Loading course form {}.", course);
        return "course/index";
    }

    @PostMapping("/courses")
    public String addNewCourse(@Valid Course course, BindingResult bindingResult, Model model) {

        this.log.info("Submitting course {}", course);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
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
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("course", "name",
                        "Course with name \"" + course.getName() + "\" is already exists.");
            }
            bindingResult.addError(error);

            return "course/index";
        }
    }

}
