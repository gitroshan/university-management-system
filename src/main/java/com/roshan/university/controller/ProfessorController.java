package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
import com.roshan.university.model.Professor;
import com.roshan.university.repository.AppRoleRepository;
import com.roshan.university.repository.AppUserRepository;
import com.roshan.university.repository.ProfessorRepository;

@Controller
public class ProfessorController {

    private Logger log = LoggerFactory.getLogger(ProfessorController.class);

    private @Autowired ProfessorRepository professorRepository;

    private @Autowired AppUserRepository appUserRepository;

    private @Autowired AppRoleRepository appRoleRepository;

    private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/professors/result")
    public String result(Model model) {

        List<Professor> professors = this.professorRepository.findAll();

        Collections.sort(professors, (professor1, professor2) -> {
            return professor1.getId().intValue() - professor2.getId().intValue();
        });

        model.addAttribute("professors", professors);
        return "professor/result";
    }

    @GetMapping("/professors")
    public String index(Professor professor) {
        this.log.info("Loading professor form {}.", professor);
        return "professor/index";
    }

    @PostMapping("/professors")
    @Transactional
    public String addNewProfessor(@Valid Professor professor, BindingResult bindingResult, Model model) {

        this.log.info("Submitting professor {}", professor);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "professor/index";
            }

            Optional<Professor> professorOptional = this.professorRepository.findByEmail(professor.getEmail());
            if (professorOptional.isPresent()) {
                this.log.error("Professor with email \"{}\" is already exists.", professor.getEmail());
                ObjectError error = new FieldError("professor", "email",
                        "Professor with email \"" + professor.getEmail() + "\" is already exists.");

                bindingResult.addError(error);

                return "professor/index";
            }

            professorOptional = this.professorRepository.findByPhoneNumber(professor.getPhoneNumber());
            if (professorOptional.isPresent()) {
                this.log.error("Professor with phone number \"{}\" is already exists.", professor.getPhoneNumber());
                ObjectError error = new FieldError("professor", "phoneNumber",
                        "Professor with phone number \"" + professor.getPhoneNumber() + "\" is already exists.");

                bindingResult.addError(error);

                return "professor/index";
            }

            AppUser appUserExisting = this.appUserRepository.findByUsername(professor.getEmail());
            if (appUserExisting != null) {
                this.log.error("User with email \"{}\" is already exists.", professor.getEmail());
                ObjectError error = new FieldError("professor", "email",
                        "User with email \"" + professor.getEmail() + "\" is already exists.");

                bindingResult.addError(error);

                return "professor/index";
            }

            AppUser appUser = new AppUser();
            appUser.setFirstName(professor.getFirstName());
            appUser.setLastName(professor.getLastName());
            appUser.setUsername(professor.getEmail());
            appUser.setPassword(this.bCryptPasswordEncoder.encode(professor.getPassword()));

            Optional<AppRole> appRole = this.appRoleRepository.findByName("ROLE_TEACHER");
            if (!appRole.isPresent()) {
                throw new RoleNotFoundException("ROLE_TEACHER is not found.");
            }
            appUser.getRoles().add(appRole.get());

            this.professorRepository.save(professor);
            this.appUserRepository.save(appUser);

            model.addAttribute("successMessage", "Professor is saved");

            List<Professor> professors = this.professorRepository.findAll();

            Collections.sort(professors, (professor1, professor2) -> {
                return professor1.getId().intValue() - professor2.getId().intValue();
            });

            model.addAttribute("professors", professors);

            return "professor/result";
        } catch (Exception e) {

            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("professor", "name", "Professor cannot be saved.");
            bindingResult.addError(error);

            return "professor/index";
        }
    }

}
