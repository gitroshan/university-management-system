package com.roshan.university.controller;

import java.security.Principal;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roshan.university.form.UserForm;
import com.roshan.university.model.AppUser;
import com.roshan.university.model.AuthenticatedUser;
import com.roshan.university.service.AppUserService;
import com.roshan.university.utils.WebUtils;

@Controller
public class ViewController {

    private @Autowired AppUserService appUserService;

    private Logger log = LoggerFactory.getLogger(ViewController.class);

    private @Autowired MessageSource messageSource;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal, Locale locale) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        System.out.println(this.messageSource.getMessage("site.name", null, locale));
        System.out.println("--------------------");
        System.out.println(principal.toString());

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authenticationToken.getPrincipal();
        System.out.println(authenticatedUser);
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "loginPage";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        this.log.info("Register page ");
        model.addAttribute("userForm", new UserForm());
        return "signup";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute UserForm userForm, BindingResult result) {

        this.log.info("Submitting user" + userForm);
        AppUser appUser = new AppUser();
        appUser.setFirstName(userForm.getFirstName());
        appUser.setLastName(userForm.getLastName());
        appUser.setPassword(userForm.getPassword());
        appUser.setUsername(userForm.getEmail());

        this.appUserService.registerUser(appUser);

        return "loginPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // (1) (en)
        // After user login successfully.
        // (vi)
        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

}
