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

import com.roshan.university.model.Level;
import com.roshan.university.repository.LevelRepository;

@Controller
public class LevelController {

    private Logger log = LoggerFactory.getLogger(LevelController.class);

    private @Autowired LevelRepository levelRepository;

    @GetMapping("/levels/result")
    public String result(Model model) {

        List<Level> levels = this.levelRepository.findAll();

        Collections.sort(levels, (level1, level2) -> {
            return level1.getId().intValue() - level2.getId().intValue();
        });

        model.addAttribute("levels", levels);
        return "level/result";
    }

    @GetMapping("/levels")
    public String index(Level level) {
        this.log.info("Loading level form {}.", level);
        return "level/index";
    }

    @PostMapping("/levels")
    public String addNewLevel(@Valid Level level, BindingResult bindingResult, Model model) {

        this.log.info("Submitting level {}", level);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "level/index";
            }

            this.levelRepository.save(level);
            model.addAttribute("successMessage", "Level is saved");

            List<Level> levels = this.levelRepository.findAll();

            Collections.sort(levels, (level1, level2) -> {
                return level1.getId().intValue() - level2.getId().intValue();
            });

            model.addAttribute("levels", levels);

            return "level/result";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("level", "name", "Level cannot be saved.");
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("level", "name",
                        "Level with name \"" + level.getName() + "\" is already exists.");
            }
            bindingResult.addError(error);

            return "level/index";
        }
    }

}
