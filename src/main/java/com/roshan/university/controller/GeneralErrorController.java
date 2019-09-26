package com.roshan.university.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SuppressWarnings({"static-method", "nls"})
public class GeneralErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "/error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error/error";
    }
}