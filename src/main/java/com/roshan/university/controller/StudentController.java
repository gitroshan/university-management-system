package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roshan.university.exception.RoleNotFoundException;
import com.roshan.university.model.AppRole;
import com.roshan.university.model.AppUser;
import com.roshan.university.model.Student;
import com.roshan.university.repository.AppRoleRepository;
import com.roshan.university.repository.AppUserRepository;
import com.roshan.university.repository.StudentRepository;

@Controller
public class StudentController {

    private Logger log = LoggerFactory.getLogger(StudentController.class);

    private @Autowired StudentRepository studentRepository;

    private @Autowired AppUserRepository appUserRepository;

    private @Autowired AppRoleRepository appRoleRepository;

    private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

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

            Optional<Student> studentOptional = this.studentRepository.findByEmail(student.getEmail());
            if (studentOptional.isPresent()) {
                this.log.error("Student with email \"{}\" is already exists.", student.getEmail());
                ObjectError error = new FieldError("student", "email",
                        "Student with email \"" + student.getEmail() + "\" is already exists.");

                bindingResult.addError(error);

                return "student/index";
            }

            studentOptional = this.studentRepository.findByPhoneNumber(student.getPhoneNumber());
            if (studentOptional.isPresent()) {
                this.log.error("Student with phone number \"{}\" is already exists.", student.getPhoneNumber());
                ObjectError error = new FieldError("student", "phoneNumber",
                        "Student with phone number \"" + student.getPhoneNumber() + "\" is already exists.");

                bindingResult.addError(error);

                return "student/index";
            }

            AppUser appUserExisting = this.appUserRepository.findByUsername(student.getEmail());
            if (appUserExisting != null) {
                this.log.error("User with email \"{}\" is already exists.", student.getEmail());
                ObjectError error = new FieldError("student", "email",
                        "User with email \"" + student.getEmail() + "\" is already exists.");

                bindingResult.addError(error);

                return "student/index";
            }

            AppUser appUser = new AppUser();
            appUser.setFirstName(student.getFirstName());
            appUser.setLastName(student.getLastName());
            appUser.setUsername(student.getEmail());
            appUser.setPassword(this.bCryptPasswordEncoder.encode(student.getPassword()));

            Optional<AppRole> appRole = this.appRoleRepository.findByName("ROLE_TEACHER");
            if (!appRole.isPresent()) {
                throw new RoleNotFoundException("ROLE_TEACHER is not found.");
            }
            appUser.getRoles().add(appRole.get());

            this.studentRepository.save(student);
            this.appUserRepository.save(appUser);

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
            bindingResult.addError(error);

            return "student/index";
        }
    }

}
