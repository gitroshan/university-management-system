package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import com.roshan.university.model.Program;
import com.roshan.university.repository.ProgramRepository;

@Controller
public class ProgramController {

    private Logger log = LoggerFactory.getLogger(ProgramController.class);

    private @Autowired ProgramRepository programRepository;

    @GetMapping("/programs/result")
    public String result(Model model) {

        List<Program> programs = this.programRepository.findAll();

        Collections.sort(programs, (program1, program2) -> {
            return program1.getId().intValue() - program2.getId().intValue();
        });

        model.addAttribute("programs", programs);
        return "program/result";
    }

    @GetMapping("/programs")
    public String index(Program program) {
        this.log.info("Loading program form {}.", program);
        return "program/index";
    }

    @PostMapping("/programs")
    public String addNewProgram(@Valid Program program, BindingResult bindingResult, Model model) {

        this.log.info("Submitting program {}", program);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "program/index";
            }

            Optional<Program> programOptional = this.programRepository.findByName(program.getName());
            if (programOptional.isPresent()) {
                this.log.error("Program with name \"{}\" is already exists.", program.getName());
                ObjectError error = new FieldError("program", "name",
                        "Program with name \"" + program.getName() + "\" is already exists.");

                bindingResult.addError(error);

                return "program/index";
            }

            this.programRepository.save(program);
            model.addAttribute("successMessage", "Program is saved");

            List<Program> programs = this.programRepository.findAll();

            Collections.sort(programs, (program1, program2) -> {
                return program1.getId().intValue() - program2.getId().intValue();
            });

            model.addAttribute("programs", programs);

            return "program/result";
        } catch (Exception e) {

            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("program", "name", "Program cannot be saved.");
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("program", "name",
                        "Program with name \"" + program.getName() + "\" is already exists.");
            }
            bindingResult.addError(error);

            return "program/index";
        }
    }

}
