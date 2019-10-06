package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;

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

import com.roshan.university.model.Classroom;
import com.roshan.university.model.ClassroomSession;
import com.roshan.university.model.Group;
import com.roshan.university.repository.ClassroomRepository;
import com.roshan.university.repository.GroupRepository;
import com.roshan.university.repository.SessionRepository;

@Controller
public class SessionController {

    private Logger log = LoggerFactory.getLogger(SessionController.class);

    private @Autowired SessionRepository sessionRepository;

    private @Autowired GroupRepository groupRepository;

    private @Autowired ClassroomRepository classroomRepository;

    @GetMapping("/sessions/result")
    public String result(Model model) {

        List<ClassroomSession> sessions = this.sessionRepository.findAll();

        Collections.sort(sessions, (session1, session2) -> {
            return session1.getId().intValue() - session2.getId().intValue();
        });

        model.addAttribute("classroomSessions", sessions);
        return "session/result";
    }

    @GetMapping("/sessions")
    public String index(ClassroomSession classroomSession, Model model) {
        this.log.info("Loading classroomSession form {}.", classroomSession);

        List<Group> groups = this.groupRepository.findAll();
        List<Classroom> classrooms = this.classroomRepository.findAll();

        model.addAttribute("groups", groups);
        model.addAttribute("classrooms", classrooms);

        return "session/index";
    }

    @PostMapping("/sessions")
    public String addNewSession(@Valid ClassroomSession classroomSession, BindingResult bindingResult, Model model) {

        this.log.info("Submitting classroomSession {}", classroomSession);
        try {
            if (bindingResult.hasErrors()) {

                this.log.info("Form has some errors");

                List<Group> groups = this.groupRepository.findAll();
                List<Classroom> classrooms = this.classroomRepository.findAll();

                model.addAttribute("groups", groups);
                model.addAttribute("classrooms", classrooms);
                return "session/index";
            }

            this.sessionRepository.save(classroomSession);
            model.addAttribute("successMessage", "ClassroomSession is saved");

            List<ClassroomSession> sessions = this.sessionRepository.findAll();

            Collections.sort(sessions, (session1, session2) -> {
                return session1.getId().intValue() - session2.getId().intValue();
            });

            model.addAttribute("classroomSessions", sessions);

            return "session/result";
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("classroomSession", "id", "Session cannot be saved.");
            bindingResult.addError(error);

            List<Group> groups = this.groupRepository.findAll();
            List<Classroom> classrooms = this.classroomRepository.findAll();

            model.addAttribute("groups", groups);
            model.addAttribute("classrooms", classrooms);

            return "session/index";
        }
    }

}
