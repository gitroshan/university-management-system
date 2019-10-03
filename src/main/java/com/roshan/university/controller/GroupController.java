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

import com.roshan.university.model.Group;
import com.roshan.university.repository.GroupRepository;

@Controller
public class GroupController {

    private Logger log = LoggerFactory.getLogger(GroupController.class);

    private @Autowired GroupRepository groupRepository;

    @GetMapping("/groups/result")
    public String result(Model model) {

        List<Group> groups = this.groupRepository.findAll();

        Collections.sort(groups, (group1, group2) -> {
            return group1.getId().intValue() - group2.getId().intValue();
        });

        model.addAttribute("groups", groups);
        return "group/result";
    }

    @GetMapping("/groups")
    public String index(Group group) {
        this.log.info("Loading group form {}.", group);
        return "group/index";
    }

    @PostMapping("/groups")
    public String addNewGroup(@Valid Group group, BindingResult bindingResult, Model model) {

        this.log.info("Submitting group {}", group);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");
                return "group/index";
            }

            this.groupRepository.save(group);
            model.addAttribute("successMessage", "Group is saved");

            List<Group> groups = this.groupRepository.findAll();

            Collections.sort(groups, (group1, group2) -> {
                return group1.getId().intValue() - group2.getId().intValue();
            });

            model.addAttribute("groups", groups);

            return "group/result";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("group", "name", "Group cannot be saved.");
            if (e instanceof DataIntegrityViolationException) {
                error = new FieldError("group", "name",
                        "Group with name \"" + group.getName() + "\" is already exists.");
            } else if (e instanceof NumberFormatException) {
                error = new FieldError("group", "capacity", "Please provide a valid number for Capacity");
            }
            bindingResult.addError(error);

            return "group/index";
        }
    }

}
