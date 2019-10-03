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

import com.roshan.university.model.Professor;
import com.roshan.university.repository.ProfessorRepository;

@Controller
public class ProfessorController {

    private Logger log = LoggerFactory.getLogger(ProfessorController.class);

    private @Autowired ProfessorRepository professorRepository;

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
    public String addNewProfessor(@Valid Professor professor, BindingResult bindingResult, Model model) {

        this.log.info("Submitting professor {}", professor);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "professor/index";
            }

            this.professorRepository.save(professor);
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
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("professor", "email",
                        "Professor with email \"" + professor.getEmail() + "\" is already exists.");
            }
            bindingResult.addError(error);

            return "professor/index";
        }
    }

}
