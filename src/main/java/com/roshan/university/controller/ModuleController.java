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
import com.roshan.university.model.Module;
import com.roshan.university.model.Professor;
import com.roshan.university.repository.CourseRepository;
import com.roshan.university.repository.ModuleRepository;
import com.roshan.university.repository.ProfessorRepository;

@Controller
public class ModuleController {

    private Logger log = LoggerFactory.getLogger(ModuleController.class);

    private @Autowired ModuleRepository moduleRepository;

    private @Autowired ProfessorRepository professorRepository;

    private @Autowired CourseRepository courseRepository;

    @GetMapping("/modules/result")
    public String result(Model model) {

        List<Module> modules = this.moduleRepository.findAll();

        Collections.sort(modules, (module1, module2) -> {
            return module1.getId().intValue() - module2.getId().intValue();
        });

        model.addAttribute("modules", modules);
        return "module/result";
    }

    @GetMapping("/modules")
    public String index(Module module, Model model) {
        this.log.info("Loading module form {}.", module);

        List<Professor> professors = this.professorRepository.findAll();
        List<Course> courses = this.courseRepository.findAll();
        model.addAttribute("professors", professors);
        model.addAttribute("courses", courses);
        return "module/index";
    }

    @PostMapping("/modules")
    public String addNewModule(@Valid Module module, BindingResult bindingResult, Model model) {

        this.log.info("Submitting module {}", module);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");

                List<Professor> professors = this.professorRepository.findAll();
                List<Course> courses = this.courseRepository.findAll();
                model.addAttribute("professors", professors);
                model.addAttribute("courses", courses);
                return "module/index";
            }

            Optional<Module> moduleOptional = this.moduleRepository.findByName(module.getName());
            if (moduleOptional.isPresent()) {
                this.log.error("Module with name \"{}\" is already exists.", module.getName());
                ObjectError error = new FieldError("module", "name",
                        "Module with name \"" + module.getName() + "\" is already exists.");

                bindingResult.addError(error);
                List<Professor> professors = this.professorRepository.findAll();
                List<Course> courses = this.courseRepository.findAll();
                model.addAttribute("professors", professors);
                model.addAttribute("courses", courses);
                return "module/index";
            }
            this.moduleRepository.save(module);
            model.addAttribute("successMessage", "Module is saved");

            List<Module> modules = this.moduleRepository.findAll();

            Collections.sort(modules, (module1, module2) -> {
                return module1.getId().intValue() - module2.getId().intValue();
            });

            model.addAttribute("modules", modules);

            return "module/result";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("module", "name", "Module cannot be saved.");
            bindingResult.addError(error);

            List<Professor> professors = this.professorRepository.findAll();
            List<Course> courses = this.courseRepository.findAll();
            model.addAttribute("professors", professors);
            model.addAttribute("courses", courses);
            return "module/index";
        }
    }

}
