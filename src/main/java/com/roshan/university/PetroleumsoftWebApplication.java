package com.roshan.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class PetroleumsoftWebApplication implements WebMvcConfigurer {

    private @Autowired LocaleChangeInterceptor localeChangeInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(PetroleumsoftWebApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor);
    }

}
