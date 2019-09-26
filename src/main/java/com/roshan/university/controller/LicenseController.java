package com.roshan.university.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.roshan.university.exception.PetroleumException;
import com.roshan.university.model.License;
import com.roshan.university.service.LicenseService;

@SuppressWarnings("nls")
@Controller
public class LicenseController {

    private Logger log = LoggerFactory.getLogger(LicenseController.class);

    private @Autowired LicenseService licenseService;

    @GetMapping("/license")
    public String licenseIndex(ModelMap model) {
        this.log.info("Rendering license view ...");
        List<License> allLicenses = this.licenseService.getAllLicenses();
        model.addAttribute("allLicenses", allLicenses);
        return "license/index";
    }

    @PostMapping("/license")
    public String submiLicense(@RequestParam String license, @RequestParam String username, ModelMap model) {

        List<String> errors = new ArrayList<>();

        try {

            this.log.info("Submitting license ...");

            if (!StringUtils.hasText(username)) {
                errors.add("Username is required.");
            }
            if (!StringUtils.hasText(license)) {
                errors.add("License key is required.");

            }
            if (!CollectionUtils.isEmpty(errors)) {
                model.addAttribute("errors", errors);
                return "license/index";
            }

            this.licenseService.submitLicenseKey(license, username);
            this.log.info("License key is submitted ...");
            model.addAttribute("message", "Your license key is validated successfully. Please log in to continue.");
            return "login";
        } catch (PetroleumException e) {
            this.log.error(e.getMessage(), e);
            errors.add(e.getMessage());
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            errors.add("Error occurred while submitting license key. Please try again later.");
        }

        model.addAttribute("errors", errors);
        return "license/index";

    }

}
